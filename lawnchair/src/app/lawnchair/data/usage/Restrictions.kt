package app.lawnchair.data.usage

import android.content.Context
import android.content.RestrictionsManager

internal object Restrictions {
    fun string(context: Context, key: String): String? {
        val restrictions = context.getSystemService(RestrictionsManager::class.java)
            ?.applicationRestrictions
            ?: return null
        return restrictions.getString(key)?.trim()?.takeIf { it.isNotEmpty() && !unresolvedLookup(it) }
    }

    private fun unresolvedLookup(value: String): Boolean {
        return value.length >= 4 && value.startsWith("\${") && value.endsWith("}")
    }
}
