package app.lawnchair.data.usage

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_device_usage")
data class DailyDeviceUsage(
    @PrimaryKey val date: String,
    val screenOnMs: Long,
    @ColumnInfo(defaultValue = "0") val updatedAt: Long = 0L,
    @ColumnInfo(defaultValue = "0") val syncedAt: Long = 0L,
)
