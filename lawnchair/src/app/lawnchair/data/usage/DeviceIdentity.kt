package app.lawnchair.data.usage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "device_identity")
data class DeviceIdentity(
    @PrimaryKey val id: Int = 1,
    val tabletId: String,
    val model: String,
    val registeredAt: Long,
)
