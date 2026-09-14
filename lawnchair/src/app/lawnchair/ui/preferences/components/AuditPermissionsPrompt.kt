package app.lawnchair.ui.preferences.components

import android.Manifest
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import app.lawnchair.data.usage.AuditPermission
import app.lawnchair.data.usage.UsageService
import com.android.launcher3.R

@Composable
fun AuditPermissionsPrompt(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var missing by remember { mutableStateOf(AuditPermission.missing(context)) }
    var dismissed by remember { mutableStateOf(false) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                missing = AuditPermission.missing(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    val request = rememberAuditPermissionRequest { missing = AuditPermission.missing(context) }
    if (dismissed || missing.isEmpty()) return
    AlertDialog(
        onDismissRequest = { dismissed = true },
        modifier = modifier,
        title = { Text(stringResource(id = R.string.permissions_needed)) },
        text = {
            Column {
                Text(stringResource(id = R.string.usage_audit_permissions_message))
                AuditPermission.required().forEach { permission ->
                    PermissionRow(
                        isChecked = permission !in missing,
                        onClick = { request(permission) },
                        permissionName = auditPermissionLabel(permission),
                    )
                }
            }
        },
        confirmButton = {
            TextButton(onClick = { dismissed = true }) {
                Text(stringResource(id = R.string.dismiss))
            }
        },
    )
}

@Composable
fun rememberAuditPermissionRequest(onResult: () -> Unit): (AuditPermission) -> Unit {
    val context = LocalContext.current
    val service = remember { UsageService.INSTANCE.get(context) }
    val locationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { onResult() }
    val backgroundLocationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { onResult() }
    val notificationLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestPermission(),
    ) { onResult() }
    return { permission ->
        when (permission) {
            AuditPermission.USAGE_ACCESS -> {
                context.startActivity(service.usageAccessIntent())
            }

            AuditPermission.LOCATION -> {
                if (service.hasLocationPermission()) {
                    context.startActivity(service.locationSettingsIntent())
                } else {
                    locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
                }
            }

            AuditPermission.BACKGROUND_LOCATION -> {
                when {
                    !service.hasLocationPermission() ->
                        locationLauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)

                    Build.VERSION.SDK_INT >= Build.VERSION_CODES.R ->
                        context.startActivity(service.locationSettingsIntent())

                    else ->
                        backgroundLocationLauncher.launch(Manifest.permission.ACCESS_BACKGROUND_LOCATION)
                }
            }

            AuditPermission.NOTIFICATIONS -> {
                notificationLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
            }

            AuditPermission.BATTERY -> {
                context.startActivity(service.batteryOptimizationIntent())
            }
        }
    }
}

@Composable
fun auditPermissionLabel(permission: AuditPermission): String = when (permission) {
    AuditPermission.USAGE_ACCESS -> stringResource(id = R.string.usage_audit_usage_permission)
    AuditPermission.LOCATION -> stringResource(id = R.string.usage_audit_ping_location_permission)
    AuditPermission.BACKGROUND_LOCATION -> stringResource(id = R.string.usage_audit_background_location_permission)
    AuditPermission.NOTIFICATIONS -> stringResource(id = R.string.usage_audit_notification_permission)
    AuditPermission.BATTERY -> stringResource(id = R.string.usage_audit_battery_permission)
}

@Composable
fun auditPermissionStatus(permission: AuditPermission, granted: Boolean): String = when (permission) {
    AuditPermission.USAGE_ACCESS -> if (granted) {
        stringResource(id = R.string.usage_audit_usage_permission_granted)
    } else {
        stringResource(id = R.string.usage_audit_usage_permission_denied)
    }

    AuditPermission.LOCATION -> if (granted) {
        stringResource(id = R.string.usage_audit_ping_location_granted)
    } else {
        stringResource(id = R.string.usage_audit_ping_location_denied)
    }

    AuditPermission.BACKGROUND_LOCATION -> if (granted) {
        stringResource(id = R.string.usage_audit_background_location_granted)
    } else {
        stringResource(id = R.string.usage_audit_background_location_denied)
    }

    AuditPermission.NOTIFICATIONS -> if (granted) {
        stringResource(id = R.string.usage_audit_notification_granted)
    } else {
        stringResource(id = R.string.usage_audit_notification_denied)
    }

    AuditPermission.BATTERY -> if (granted) {
        stringResource(id = R.string.usage_audit_battery_granted)
    } else {
        stringResource(id = R.string.usage_audit_battery_denied)
    }
}
