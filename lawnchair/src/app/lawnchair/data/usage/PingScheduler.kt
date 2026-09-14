package app.lawnchair.data.usage

import android.content.Context

object PingScheduler {
    fun sync(context: Context) {
        val app = context.applicationContext
        if (LocationPermission.hasFine(app) && DeviceSerial.resolve(app) != null) {
            LocationPingService.start(app)
            LocationPingWorker.enqueue(app)
        } else {
            LocationPingService.stop(app)
            LocationPingWorker.cancel(app)
        }
    }
}
