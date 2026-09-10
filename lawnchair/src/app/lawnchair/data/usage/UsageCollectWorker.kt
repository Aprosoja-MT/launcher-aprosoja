package app.lawnchair.data.usage

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import java.util.concurrent.TimeUnit

class UsageCollectWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        UsageService.INSTANCE.get(applicationContext).collectToday()
        LauncherSyncWorker.enqueueOnce(applicationContext)
        return Result.success()
    }

    companion object {
        private const val UNIQUE_NAME = "usage_collect"

        fun enqueue(context: Context) {
            val request = PeriodicWorkRequestBuilder<UsageCollectWorker>(15, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UNIQUE_NAME,
                ExistingPeriodicWorkPolicy.KEEP,
                request,
            )
        }

        fun enqueueOnce(context: Context) {
            WorkManager.getInstance(context).enqueue(
                OneTimeWorkRequestBuilder<UsageCollectWorker>().build(),
            )
        }
    }
}
