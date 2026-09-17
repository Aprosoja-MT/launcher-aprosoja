package app.lawnchair.data.usage

import android.content.Context
import android.location.Location

object PingCollector {
    private const val RETENTION_MS = 30L * 24 * 60 * 60 * 1000

    suspend fun collectOnce(context: Context, dao: UsageDao): Boolean {
        if (!isCollectable(context)) return false
        val location = LocationFix.resolve(context) ?: return false
        val mode = MovementState.mode(context)
        val latest = dao.getLatestPing()
        if (latest != null && LocationFix.timestampOf(location) - latest.timestamp < mode.minGapMs) {
            return false
        }
        persist(context, dao, location, mode)
        return true
    }

    suspend fun collectLocation(
        context: Context,
        dao: UsageDao,
        location: Location,
        mode: PingMode,
    ): Boolean {
        if (!isCollectable(context)) return false
        if (!LocationFix.isValid(location)) return false
        persist(context, dao, location, mode)
        return true
    }

    private fun isCollectable(context: Context): Boolean {
        return DeviceSerial.resolve(context) != null && LocationPermission.hasFine(context)
    }

    private suspend fun persist(context: Context, dao: UsageDao, location: Location, mode: PingMode) {
        dao.insertPing(
            LocationPing(
                timestamp = LocationFix.timestampOf(location),
                latitude = location.latitude,
                longitude = location.longitude,
                accuracyMeters = if (location.hasAccuracy()) location.accuracy else 0f,
                speedMps = if (location.hasSpeed()) location.speed else null,
                intervalSec = mode.reportSec,
            ),
        )
        dao.prunePings(System.currentTimeMillis() - RETENTION_MS)
        LauncherSyncWorker.enqueueOnce(context)
    }
}
