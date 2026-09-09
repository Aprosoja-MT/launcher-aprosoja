package app.lawnchair.data.usage

import android.content.Context

object PingScheduler {
    fun sync(context: Context) {
        val app = context.applicationContext
        val intervalMin = PingInterval.resolve(app)
        if (PingInterval.isRoute(intervalMin) &&
            LocationPermission.hasFine(app) &&
            DeviceSerial.resolve(app) != null
        ) {
            LocationPingWorker.cancel(app)
            LocationPingService.start(app)
        } else {
            LocationPingService.stop(app)
            LocationPingWorker.enqueue(app, intervalMin.coerceAtLeast(PingInterval.ROUTE_THRESHOLD))
        }
    }
}
