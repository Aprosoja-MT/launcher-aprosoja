package app.lawnchair.data.usage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "daily_device_usage")
data class DailyDeviceUsage(
    @PrimaryKey val date: String,
    val screenOnMs: Long,
)
