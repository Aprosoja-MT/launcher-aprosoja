package app.lawnchair.data.usage

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.sqlite.db.SupportSQLiteQuery
import kotlinx.coroutines.flow.Flow

@Dao
interface UsageDao {
    @Query("SELECT * FROM device_identity WHERE id = 1")
    fun observeIdentity(): Flow<DeviceIdentity?>

    @Query("SELECT * FROM device_identity WHERE id = 1")
    suspend fun getIdentity(): DeviceIdentity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertIdentity(identity: DeviceIdentity)

    @Query("SELECT * FROM watched_apps")
    fun observeWatched(): Flow<List<WatchedApp>>

    @Query("SELECT * FROM watched_apps WHERE enabled = 1")
    suspend fun getEnabledWatched(): List<WatchedApp>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertWatched(app: WatchedApp)

    @Query("DELETE FROM watched_apps WHERE packageName = :packageName")
    suspend fun deleteWatched(packageName: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDeviceUsage(usage: DailyDeviceUsage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAppUsage(usage: DailyAppUsage)

    @Query("SELECT * FROM daily_device_usage WHERE date = :date")
    fun observeDeviceUsage(date: String): Flow<DailyDeviceUsage?>

    @Query("SELECT * FROM daily_app_usage WHERE date = :date")
    fun observeAppUsage(date: String): Flow<List<DailyAppUsage>>

    @Query("DELETE FROM daily_device_usage WHERE date < :cutoff")
    suspend fun pruneDeviceUsage(cutoff: String)

    @Query("DELETE FROM daily_app_usage WHERE date < :cutoff")
    suspend fun pruneAppUsage(cutoff: String)

    @RawQuery
    suspend fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int
}
