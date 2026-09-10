package app.lawnchair.data.usage

import android.content.Context

object PingScheduler {
    fun sync(context: Context) {
        val app = context.applicationContext
        val intervalMin = PingInterval.resolve(app)
        if (LocationPermission.hasFine(app) && DeviceSerial.resolve(app) != null) {
            LocationPingService.start(app)
            LocationPingWorker.enqueue(app, intervalMin.coerceAtLeast(PingInterval.ROUTE_THRESHOLD))
        } else {
            LocationPingService.stop(app)
            LocationPingWorker.cancel(app)
        }
    }
}
