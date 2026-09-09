package app.lawnchair.data.usage

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "watched_apps")
data class WatchedApp(
    @PrimaryKey val packageName: String,
    val label: String,
    val enabled: Boolean = true,
)
