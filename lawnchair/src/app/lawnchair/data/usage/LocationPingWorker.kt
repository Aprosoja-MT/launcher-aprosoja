package app.lawnchair.data.usage

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class LocationPingWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        PingScheduler.sync(applicationContext)
        UsageService.INSTANCE.get(applicationContext).collectPing()
        return Result.success()
    }

    companion object {
        private const val UNIQUE_NAME = "location_ping"

        fun enqueue(context: Context) {
            val request = PeriodicWorkRequestBuilder<LocationPingWorker>(
                PingRules.FALLBACK_PING_MINUTES,
                TimeUnit.MINUTES,
            ).build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UNIQUE_NAME,
                ExistingPeriodicWorkPolicy.UPDATE,
                request,
            )
        }

        fun cancel(context: Context) {
            WorkManager.getInstance(context).cancelUniqueWork(UNIQUE_NAME)
        }
    }
}
