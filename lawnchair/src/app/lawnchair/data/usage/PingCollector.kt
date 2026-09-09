package app.lawnchair.data.usage

import android.content.Context
import android.location.Location

object PingCollector {
    private const val RETENTION_MS = 30L * 24 * 60 * 60 * 1000

    suspend fun collectOnce(context: Context, dao: UsageDao): Boolean {
        if (DeviceSerial.resolve(context) == null) return false
        if (!LocationPermission.hasFine(context)) return false
        val location = LocationFix.resolve(context) ?: return false
        persist(context, dao, location)
        return true
    }

    suspend fun collectLocation(context: Context, dao: UsageDao, location: Location): Boolean {
        if (DeviceSerial.resolve(context) == null) return false
        if (!LocationPermission.hasFine(context)) return false
        if (!LocationFix.isValid(location)) return false
        persist(context, dao, location)
        return true
    }

    private suspend fun persist(context: Context, dao: UsageDao, location: Location) {
        val intervalMin = PingInterval.resolve(context)
        val timestamp = if (location.time > 0L) location.time else System.currentTimeMillis()
        val latest = dao.getLatestPing()
        if (latest != null && timestamp - latest.timestamp < intervalMin * 60_000L) {
            return
        }
        dao.insertPing(
            LocationPing(
                timestamp = timestamp,
                latitude = location.latitude,
                longitude = location.longitude,
                accuracyMeters = if (location.hasAccuracy()) location.accuracy else 0f,
                speedMps = if (location.hasSpeed()) location.speed else null,
                intervalMin = intervalMin,
            ),
        )
        dao.prunePings(System.currentTimeMillis() - RETENTION_MS)
    }
}
