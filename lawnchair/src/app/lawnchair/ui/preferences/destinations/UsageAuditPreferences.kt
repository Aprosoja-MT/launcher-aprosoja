package app.lawnchair.ui.preferences.destinations

import android.text.format.DateUtils
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import app.lawnchair.data.usage.SerialSource
import app.lawnchair.data.usage.UsageAuditUiState
import app.lawnchair.data.usage.UsageService
import app.lawnchair.ui.preferences.LocalIsExpandedScreen
import app.lawnchair.ui.preferences.components.AppItem
import app.lawnchair.ui.preferences.components.controls.ClickablePreference
import app.lawnchair.ui.preferences.components.controls.TextPreference
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
        initialValue = UsageAuditUiState(
            tabletId = null,
            serialValid = false,
            serialSource = SerialSource.NONE,
            debugOverride = "",
            model = "",
            hasUsagePermission = false,
            screenOnMs = 0L,
            watched = emptyMap(),
            appUsageByPackage = emptyMap(),
        ),
    )
    val lifecycleOwner = LocalLifecycleOwner.current
    var hasUsagePermission by remember { mutableStateOf(service.hasUsagePermission()) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                hasUsagePermission = service.hasUsagePermission()
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
    val permissionText = if (hasUsagePermission) {
        stringResource(id = R.string.usage_audit_usage_permission_granted)
    } else {
        stringResource(id = R.string.usage_audit_usage_permission_denied)
    }

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
                                color = if (hasUsagePermission) {
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
            items = uniqueApps,
            isFirstChild = false,
            dividerStartIndent = 40.dp,
            heading = { stringResource(id = R.string.usage_audit_watched_apps) },
            key = { _, app -> app.key.toString() },
        ) { _, app ->
            val packageName = app.key.componentName.packageName
            val watched = uiState.watched[packageName]?.enabled == true
            val usage = uiState.appUsageByPackage[packageName]
            AppItem(
                app = app,
                onClick = {
                    scope.launch {
                        service.setWatched(packageName, app.label, !watched)
                    }
                },
                widget = {
                    Checkbox(
                        checked = watched,
                        onCheckedChange = null,
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
