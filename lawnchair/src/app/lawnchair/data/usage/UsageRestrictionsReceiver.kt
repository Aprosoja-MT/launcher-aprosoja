package app.lawnchair.data.usage

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class UsageRestrictionsReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Intent.ACTION_APPLICATION_RESTRICTIONS_CHANGED) return
        UsageCollectWorker.enqueueOnce(context)
        LauncherSyncWorker.enqueueOnce(context)
        UsageService.INSTANCE.get(context).refresh()
    }
}
