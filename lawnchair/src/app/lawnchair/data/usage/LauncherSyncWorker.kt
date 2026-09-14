package app.lawnchair.data.usage

import android.content.Context
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import app.lawnchair.data.AppDatabase
import java.util.concurrent.TimeUnit

class LauncherSyncWorker(
    context: Context,
    params: WorkerParameters,
) : CoroutineWorker(context, params) {
    override suspend fun doWork(): Result {
        val dao = AppDatabase.INSTANCE.get(applicationContext).usageDao()
        LauncherSyncClient.sync(applicationContext, dao)
        scheduleNext(applicationContext)
        return Result.success()
    }

    companion object {
        private const val UNIQUE_LEGACY_PERIODIC = "launcher_audit_sync"
        private const val UNIQUE_CHAIN = "launcher_audit_sync_chain"
        private const val UNIQUE_ONCE = "launcher_audit_sync_once"
        private const val INTERVAL_MINUTES = PingRules.SYNC_IDLE_MINUTES

        private fun request(delayMinutes: Long) = OneTimeWorkRequestBuilder<LauncherSyncWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiredNetworkType(NetworkType.CONNECTED)
                    .build(),
            )
            .setInitialDelay(delayMinutes, TimeUnit.MINUTES)
            .build()

        fun enqueue(context: Context) {
            val manager = WorkManager.getInstance(context)
            manager.cancelUniqueWork(UNIQUE_LEGACY_PERIODIC)
            manager.enqueueUniqueWork(
                UNIQUE_CHAIN,
                ExistingWorkPolicy.KEEP,
                request(INTERVAL_MINUTES),
            )
        }

        fun enqueueOnce(context: Context) {
            WorkManager.getInstance(context).enqueueUniqueWork(
                UNIQUE_ONCE,
                ExistingWorkPolicy.KEEP,
                request(0),
            )
        }

        private fun scheduleNext(context: Context) {
            WorkManager.getInstance(context).enqueueUniqueWork(
                UNIQUE_CHAIN,
                ExistingWorkPolicy.REPLACE,
                request(INTERVAL_MINUTES),
            )
        }
    }
}
