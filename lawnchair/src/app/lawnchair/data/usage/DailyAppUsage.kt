package app.lawnchair.data.usage

import androidx.room.ColumnInfo
import androidx.room.Entity

@Entity(tableName = "daily_app_usage", primaryKeys = ["date", "packageName"])
data class DailyAppUsage(
    val date: String,
    val packageName: String,
    val openCount: Int,
    val foregroundMs: Long,
    @ColumnInfo(defaultValue = "0") val updatedAt: Long = 0L,
    @ColumnInfo(defaultValue = "0") val syncedAt: Long = 0L,
)
