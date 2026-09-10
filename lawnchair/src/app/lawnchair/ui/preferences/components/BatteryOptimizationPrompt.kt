package app.lawnchair.ui.preferences.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import app.lawnchair.data.usage.BatteryOptimization
import com.android.launcher3.R

@Composable
fun BatteryOptimizationPrompt(
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var visible by remember { mutableStateOf(!BatteryOptimization.isIgnoring(context)) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event ->
            if (event == Lifecycle.Event.ON_RESUME) {
                visible = !BatteryOptimization.isIgnoring(context)
            }
        }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    if (!visible) return
    AlertDialog(
        onDismissRequest = { visible = false },
        modifier = modifier,
        title = { Text(stringResource(id = R.string.usage_audit_battery_title)) },
        text = { Text(stringResource(id = R.string.usage_audit_battery_message)) },
        confirmButton = {
            Button(
                onClick = {
                    context.startActivity(BatteryOptimization.requestIntent(context))
                },
            ) {
                Text(stringResource(id = R.string.usage_audit_battery_confirm))
            }
        },
        dismissButton = {
            TextButton(onClick = { visible = false }) {
                Text(stringResource(android.R.string.cancel))
            }
        },
    )
}
