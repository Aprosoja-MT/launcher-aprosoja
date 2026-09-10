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
import app.lawnchair.data.usage.PingIntervalSource
import app.lawnchair.data.usage.SerialSource
import app.lawnchair.data.usage.UsageAuditUiState
import app.lawnchair.data.usage.UsageService
import app.lawnchair.ui.preferences.LocalIsExpandedScreen
import app.lawnchair.ui.preferences.components.BatteryOptimizationPrompt
import app.lawnchair.ui.preferences.components.AppItem
import app.lawnchair.ui.preferences.components.controls.ClickablePreference
import app.lawnchair.ui.preferences.components.controls.TextPreference
import app.lawnchair.ui.preferences.components.layout.ClickableIcon
import app.lawnchair.ui.preferences.components.layout.PreferenceLayoutLazyColumn
import app.lawnchair.ui.preferences.components.layout.PreferenceTemplate
import app.lawnchair.ui.preferences.components.layout.preferenceGroupItems
import app.lawnchair.util.appsState
import com.android.launcher3.R
import kotlinx.coroutines.launch

@Composable
fun UsageAuditPreferences(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val service = remember { UsageService.INSTANCE.get(context) }
    val uiState by service.observeUiState().collectAsStateWithLifecycle(
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
        SerialSource.BUILD -> stringResource(id = R.string.usage_audit_serial_source_build)
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
    val permissionText = if (uiState.hasUsagePermission) {
        stringResource(id = R.string.usage_audit_usage_permission_granted)
    } else {
        stringResource(id = R.string.usage_audit_usage_permission_denied)
    }
    val locationPermissionText = if (uiState.hasLocationPermission) {
        stringResource(id = R.string.usage_audit_ping_location_granted)
    } else {
        stringResource(id = R.string.usage_audit_ping_location_denied)
    }
    val pingSourceText = when (uiState.pingIntervalSource) {
        PingIntervalSource.KNOX -> stringResource(id = R.string.usage_audit_ping_source_knox)
        PingIntervalSource.DEBUG -> stringResource(id = R.string.usage_audit_ping_source_debug)
        PingIntervalSource.DEFAULT -> stringResource(id = R.string.usage_audit_ping_source_default)
    }
    val pingIntervalText = if (uiState.routeActive) {
        stringResource(id = R.string.usage_audit_ping_interval_route, uiState.pingIntervalMin)
    } else {
        stringResource(
            id = R.string.usage_audit_ping_interval_value,
            uiState.pingIntervalMin,
            pingSourceText,
        )
    }
    val syncStatus = when {
        !uiState.syncConfigured -> stringResource(id = R.string.usage_audit_sync_missing)
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

    BatteryOptimizationPrompt()
    PreferenceLayoutLazyColumn(
        label = stringResource(id = R.string.usage_audit_label),
        modifier = modifier,
        backArrowVisible = !LocalIsExpandedScreen.current,
    ) {
        preferenceGroupItems(
            count = 4,
            isFirstChild = true,
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
                2 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_usage_permission)) },
                        description = {
                            Text(
                                text = permissionText,
                                color = if (uiState.hasUsagePermission) {
                                    MaterialTheme.colorScheme.onSurfaceVariant
                                } else {
                                    MaterialTheme.colorScheme.error
                                },
                            )
                        },
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
            count = 3,
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
                1 -> {
                    ClickablePreference(
                        label = stringResource(id = R.string.usage_audit_open_usage_settings),
                        onClick = { context.startActivity(service.usageAccessIntent()) },
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
                                color = if (uiState.syncConfigured) {
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
            count = 5,
            isFirstChild = false,
            heading = { stringResource(id = R.string.usage_audit_ping_label) },
        ) { index ->
            when (index) {
                0 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_ping_interval)) },
                        description = { Text(pingIntervalText) },
                    )
                }
                1 -> {
                    PreferenceTemplate(
                        title = { Text(stringResource(id = R.string.usage_audit_ping_location_permission)) },
                        description = {
                            Text(
                                text = locationPermissionText,
                                color = if (uiState.hasLocationPermission) {
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
                        label = stringResource(id = R.string.usage_audit_ping_open_location_settings),
                        onClick = { context.startActivity(service.locationSettingsIntent()) },
                    )
                }
                3 -> {
                    ClickablePreference(
                        label = stringResource(id = R.string.usage_audit_ping_now),
                        onClick = {
                            scope.launch {
                                service.collectPing()
                            }
                        },
                    )
                }
                else -> {
                    TextPreference(
                        value = uiState.debugPingInterval,
                        onChange = { value ->
                            scope.launch {
                                service.setDebugPingInterval(value)
                            }
                        },
                        label = stringResource(id = R.string.usage_audit_ping_interval_override),
                        description = { current ->
                            current.ifBlank { emptyOverride }
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
                                ping.intervalMin,
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
            val watched = uiState.watched[packageName]?.enabled == true
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
