package app.lawnchair.ui.preferences.destinations

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.text.format.DateUtils
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Place
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.lawnchair.data.usage.AuditPermission
import app.lawnchair.data.usage.PingMode
import app.lawnchair.data.usage.SerialSource
import app.lawnchair.data.usage.UsageAuditUiState
import app.lawnchair.data.usage.UsageService
import app.lawnchair.ui.preferences.LocalIsExpandedScreen
import app.lawnchair.ui.preferences.components.AppItem
import app.lawnchair.ui.preferences.components.auditPermissionLabel
import app.lawnchair.ui.preferences.components.auditPermissionStatus
import app.lawnchair.ui.preferences.components.controls.ClickablePreference
import app.lawnchair.ui.preferences.components.controls.TextPreference
import app.lawnchair.ui.preferences.components.layout.ClickableIcon
import app.lawnchair.ui.preferences.components.layout.PreferenceLayoutLazyColumn
import app.lawnchair.ui.preferences.components.layout.PreferenceTemplate
import app.lawnchair.ui.preferences.components.layout.preferenceGroupItems
import app.lawnchair.ui.preferences.components.rememberAuditPermissionRequest
import app.lawnchair.util.appsState
import com.android.launcher3.R
import kotlinx.coroutines.launch

@Composable
fun UsageAuditPreferences(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val service = remember { UsageService.INSTANCE.get(context) }
    val uiState by remember { service.observeUiState() }.collectAsStateWithLifecycle(
        initialValue = UsageAuditUiState.Empty,
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                service.refresh()
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    val apps by appsState()
    val uniqueApps = remember(apps) {
        apps.distinctBy { it.key.componentName.packageName }
    }
    val scope = rememberCoroutineScope()
    val emptyOverride = stringResource(id = R.string.usage_audit_serial_override_empty)
    val serialSourceText = when (uiState.serialSource) {
        SerialSource.KNOX -> stringResource(id = R.string.usage_audit_serial_source_knox)
        SerialSource.DEBUG -> stringResource(id = R.string.usage_audit_serial_source_debug)
        SerialSource.NONE -> stringResource(id = R.string.usage_audit_serial_missing)
    }
    val serialText = if (uiState.serialValid) {
        stringResource(
            id = R.string.usage_audit_serial_value,
            uiState.tabletId.orEmpty(),
            serialSourceText,
        )
    } else {
        stringResource(id = R.string.usage_audit_serial_missing)
    }
    val requestPermission = rememberAuditPermissionRequest { service.refresh() }
    val auditPermissions = remember { AuditPermission.required() }
    val pingModeText = when (uiState.pingMode) {
        PingMode.IDLE -> stringResource(id = R.string.usage_audit_ping_mode_idle)
        PingMode.MOVING -> stringResource(id = R.string.usage_audit_ping_mode_moving)
    }
    val syncError = uiState.lastSyncError
    val syncStatus = when {
        !uiState.syncConfigured -> stringResource(id = R.string.usage_audit_sync_missing)

        syncError != null -> stringResource(id = R.string.usage_audit_sync_error, syncError)

        uiState.lastSyncAt <= 0L -> stringResource(id = R.string.usage_audit_sync_never)

        else -> stringResource(
            id = R.string.usage_audit_sync_ready,
            DateUtils.getRelativeTimeSpanString(
                uiState.lastSyncAt,
                System.currentTimeMillis(),
                DateUtils.MINUTE_IN_MILLIS,
            ).toString(),
        )
    }

    PreferenceLayoutLazyColumn(
        label = stringResource(id = R.string.usage_audit_label),
        modifier = modifier,
        backArrowVisible = !LocalIsExpandedScreen.current,
    ) {
        preferenceGroupItems(
            items = auditPermissions,
            isFirstChild = true,
            heading = { stringResource(id = R.string.usage_audit_permissions) },
            key = { _, permission -> permission.name },
        ) { _, permission ->
            ClickablePreference(
                label = auditPermissionLabel(permission),
                subtitle = auditPermissionStatus(permission, uiState.isGranted(permission)),
                onClick = { requestPermission(permission) },
            )
        }
        preferenceGroupItems(
            count = 3,
            isFirstChild = false,
            heading = { stringResource(id = R.string.usage_audit_serial) },
        ) { index ->
            when (index) {
                0 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_serial)) },
                        description = {
                            Text(
                                text = serialText,
                                color = if (uiState.serialValid) {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                            )
                        },
                    )
                }

                1 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_model)) },
                        description = { Text(uiState.model) },
                    )
                }

                else -> {
                    TextPreference(
                        value = uiState.debugOverride,
                        onChange = { value ->
                            scope.launch {
                                service.setDebugSerial(value)
                            }
                        },
                        label = stringResource(id = R.string.usage_audit_serial_override),
                        description = { current ->
                            current.ifBlank { emptyOverride }
                        },
                    )
                }
            }
        }
        preferenceGroupItems(
            count = 2,
            isFirstChild = false,
            heading = { stringResource(id = R.string.usage_audit_screen_time_today) },
        ) { index ->
            when (index) {
                0 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_screen_time_today)) },
                        description = { Text(DateUtils.formatElapsedTime(uiState.screenOnMs / 1000)) },
                    )
                }

                else -> {
                    ClickablePreference(
                        label = stringResource(id = R.string.usage_audit_collect_now),
                        onClick = {
                            scope.launch {
                                service.collectToday()
                            }
                        },
                    )
                }
            }
        }
        preferenceGroupItems(
            count = 6,
            isFirstChild = false,
            heading = { stringResource(id = R.string.usage_audit_sync) },
        ) { index ->
            when (index) {
                0 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_sync)) },
                        description = {
                            Text(
                                text = syncStatus,
                                color = if (uiState.syncConfigured && syncError == null) {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                            )
                        },
                    )
                }

                1 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_username)) },
                        description = {
                            Text(
                                text = uiState.username
                                    ?: stringResource(id = R.string.usage_audit_username_missing),
                                color = if (uiState.username != null) {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                            )
                        },
                    )
                }

                2 -> {
                    ClickablePreference(
                        label = stringResource(id = R.string.usage_audit_sync_now),
                        onClick = {
                            scope.launch {
                                service.syncNow()
                                service.refresh()
                            }
                        },
                    )
                }

                3 -> {
                    TextPreference(
                        value = uiState.debugUsername,
                        onChange = { value -> service.setDebugUsername(value) },
                        label = stringResource(id = R.string.usage_audit_username_override),
                        description = { current ->
                            current.ifBlank { emptyOverride }
                        },
                    )
                }

                4 -> {
                    TextPreference(
                        value = uiState.debugApiUrl,
                        onChange = { value -> service.setDebugApiUrl(value) },
                        label = stringResource(id = R.string.usage_audit_sync_url_override),
                        description = { current ->
                            current.ifBlank { emptyOverride }
                        },
                    )
                }

                else -> {
                    TextPreference(
                        value = uiState.debugBootstrap,
                        onChange = { value -> service.setDebugBootstrap(value) },
                        label = stringResource(id = R.string.usage_audit_sync_secret_override),
                        description = { current ->
                            current.ifBlank { emptyOverride }
                        },
                    )
                }
            }
        }
        preferenceGroupItems(
            count = 2,
            isFirstChild = false,
            heading = { stringResource(id = R.string.usage_audit_ping_label) },
        ) { index ->
            when (index) {
                0 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_ping_mode)) },
                        description = { Text(pingModeText) },
                    )
                }

                else -> {
                    ClickablePreference(
                        label = stringResource(id = R.string.usage_audit_ping_now),
                        onClick = {
                            scope.launch {
                                service.collectPing()
                            }
                        },
                    )
                }
            }
        }
        if (uiState.recentPings.isEmpty()) {
            preferenceGroupItems(
                count = 1,
                isFirstChild = false,
                heading = { stringResource(id = R.string.usage_audit_ping_recent) },
            ) {
                PreferenceTemplate(
                    title = { Text(stringResource(id = R.string.usage_audit_ping_empty)) },
                )
            }
        } else {
            preferenceGroupItems(
                items = uiState.recentPings,
                isFirstChild = false,
                heading = { stringResource(id = R.string.usage_audit_ping_recent) },
                key = { _, ping -> ping.id },
            ) { _, ping ->
                PreferenceTemplate(
                    title = {
                        Text(
                            DateUtils.getRelativeTimeSpanString(
                                ping.timestamp,
                                System.currentTimeMillis(),
                                DateUtils.MINUTE_IN_MILLIS,
                            ).toString(),
                        )
                    },
                    description = {
                        Text(
                            stringResource(
                                id = R.string.usage_audit_ping_point,
                                DateUtils.formatDateTime(
                                    context,
                                    ping.timestamp,
                                    DateUtils.FORMAT_SHOW_TIME or DateUtils.FORMAT_SHOW_DATE,
                                ),
                                ping.latitude,
                                ping.longitude,
                                ping.accuracyMeters,
                                ping.intervalSec,
                            ),
                        )
                    },
                    endWidget = {
                        ClickableIcon(
                            imageVector = Icons.Rounded.Place,
                            onClick = { openPingOnMaps(context, ping.latitude, ping.longitude) },
                            tint = MaterialTheme.colorScheme.primary,
                        )
                    },
                )
            }
        }
        preferenceGroupItems(
            items = uniqueApps,
            isFirstChild = false,
            dividerStartIndent = 40.dp,
            heading = {
                if (uiState.knoxWatched) {
                    stringResource(id = R.string.usage_audit_watched_apps_knox)
                } else {
                    stringResource(id = R.string.usage_audit_watched_apps)
                }
            },
            key = { _, app -> app.key.toString() },
        ) { _, app ->
            val packageName = app.key.componentName.packageName
            val watched = packageName in uiState.watched
            val usage = uiState.appUsageByPackage[packageName]
            AppItem(
                app = app,
                onClick = {
                    if (!uiState.knoxWatched) {
                        scope.launch {
                            service.setWatched(packageName, app.label, !watched)
                        }
                    }
                },
                widget = {
                    Checkbox(
                        checked = watched,
                        onCheckedChange = null,
                        enabled = !uiState.knoxWatched,
                    )
                },
                endWidget = if (watched && usage != null) {
                    {
                        Text(
                            text = stringResource(
                                id = R.string.usage_audit_app_stats,
                                usage.openCount,
                                DateUtils.formatElapsedTime(usage.foregroundMs / 1000),
                            ),
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                } else {
                    null
                },
            )
        }
    }
}

private fun openPingOnMaps(context: Context, latitude: Double, longitude: Double) {
    val uri = Uri.parse("geo:$latitude,$longitude?q=$latitude,$longitude")
    try {
        context.startActivity(Intent(Intent.ACTION_VIEW, uri))
    } catch (_: Exception) {
    }
}
