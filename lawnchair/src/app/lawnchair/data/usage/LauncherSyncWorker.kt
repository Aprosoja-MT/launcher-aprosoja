package app.lawnchair.data.usage

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import app.lawnchair.data.AppDatabase
import java.util.concurrent.TimeUnit

class LauncherSyncWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        if (!LauncherApiConfig.isConfigured(applicationContext)) {
            return Result.success()
        }
        val dao = AppDatabase.INSTANCE.get(applicationContext).usageDao()
        return if (LauncherSyncClient.sync(applicationContext, dao)) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        private const val UNIQUE_PERIODIC = "launcher_audit_sync"
        private const val UNIQUE_ONCE = "launcher_audit_sync_once"

        fun enqueue(context: Context) {
            val request = PeriodicWorkRequestBuilder<LauncherSyncWorker>(15, TimeUnit.MINUTES)
                .build()
            WorkManager.getInstance(context).enqueueUniquePeriodicWork(
                UNIQUE_PERIODIC,
                ExistingPeriodicWorkPolicy.KEEP,
                request,
            )
        }

        fun enqueueOnce(context: Context) {
            WorkManager.getInstance(context).enqueueUniqueWork(
                UNIQUE_ONCE,
                ExistingWorkPolicy.KEEP,
                OneTimeWorkRequestBuilder<LauncherSyncWorker>().build(),
            )
        }
    }
}
