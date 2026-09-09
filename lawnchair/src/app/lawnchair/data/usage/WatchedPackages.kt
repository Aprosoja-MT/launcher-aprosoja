package app.lawnchair.data.usage

import android.content.Context
import android.content.RestrictionsManager
import android.content.pm.PackageManager

object WatchedPackages {
    const val RESTRICTION_KEY = "watched_packages"

    fun fromRestrictions(context: Context): List<String>? {
        val restrictions = context.getSystemService(RestrictionsManager::class.java)
            ?.applicationRestrictions
            ?: return null
        if (!restrictions.containsKey(RESTRICTION_KEY)) return null
        return parse(restrictions.getString(RESTRICTION_KEY).orEmpty())
    }

    fun isControlled(context: Context): Boolean = fromRestrictions(context) != null

    suspend fun sync(context: Context, dao: UsageDao) {
        val packages = fromRestrictions(context) ?: return
        dao.deleteAllWatched()
        for (packageName in packages) {
            dao.upsertWatched(
                WatchedApp(
                    packageName = packageName,
                    label = label(context, packageName),
                    enabled = true,
                ),
            )
        }
    }

    fun parse(raw: String): List<String> {
        return raw.split(',', ';', '\n')
            .map { it.trim() }
            .filter { packageName ->
                packageName.contains('.') && packageName.none { it.isWhitespace() }
            }
            .distinct()
    }

    private fun label(context: Context, packageName: String): String {
        return try {
            val manager = context.packageManager
            val info = manager.getApplicationInfo(packageName, 0)
            manager.getApplicationLabel(info).toString()
        } catch (_: PackageManager.NameNotFoundException) {
            packageName
        }
    }
}
