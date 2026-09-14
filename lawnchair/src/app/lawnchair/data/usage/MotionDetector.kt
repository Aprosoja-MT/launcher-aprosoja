package app.lawnchair.data.usage

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager
import android.hardware.TriggerEvent
import android.hardware.TriggerEventListener
import androidx.core.content.getSystemService

class MotionDetector(
    context: Context,
    private val onMotion: () -> Unit,
) {
    private val sensorManager = context.applicationContext.getSystemService<SensorManager>()
    private val sensor = sensorManager?.getDefaultSensor(Sensor.TYPE_SIGNIFICANT_MOTION)
    private var armed = false

    private val listener = object : TriggerEventListener() {
        override fun onTrigger(event: TriggerEvent?) {
            armed = false
            onMotion()
        }
    }

    fun arm() {
        if (armed) return
        val manager = sensorManager ?: return
        val target = sensor ?: return
        armed = try {
            manager.requestTriggerSensor(listener, target)
        } catch (_: Exception) {
            false
        }
    }

    fun stop() {
        if (!armed) return
        armed = false
        val manager = sensorManager ?: return
        val target = sensor ?: return
        try {
            manager.cancelTriggerSensor(listener, target)
        } catch (_: Exception) {
        }
    }
}
