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

    @Query("DELETE FROM watched_apps")
    suspend fun deleteAllWatched()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDeviceUsage(usage: DailyDeviceUsage)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertAppUsage(usage: DailyAppUsage)

    @Query("SELECT * FROM daily_device_usage WHERE date = :date")
    fun observeDeviceUsage(date: String): Flow<DailyDeviceUsage?>

    @Query("SELECT * FROM daily_app_usage WHERE date = :date")
    fun observeAppUsage(date: String): Flow<List<DailyAppUsage>>

    @Query("SELECT * FROM daily_device_usage WHERE date = :date")
    suspend fun getDeviceUsage(date: String): DailyDeviceUsage?

    @Query("SELECT * FROM daily_app_usage WHERE date = :date AND packageName = :packageName")
    suspend fun getAppUsage(date: String, packageName: String): DailyAppUsage?

    @Query("SELECT * FROM daily_device_usage WHERE updatedAt > syncedAt ORDER BY date ASC LIMIT :limit")
    suspend fun getPendingDeviceUsages(limit: Int): List<DailyDeviceUsage>

    @Query(
        "SELECT * FROM daily_app_usage WHERE updatedAt > syncedAt ORDER BY date ASC, packageName ASC LIMIT :limit",
    )
    suspend fun getPendingAppUsages(limit: Int): List<DailyAppUsage>

    @Query("UPDATE daily_device_usage SET syncedAt = :updatedAt WHERE date = :date AND updatedAt = :updatedAt")
    suspend fun markDeviceUsageSynced(date: String, updatedAt: Long)

    @Query(
        "UPDATE daily_app_usage SET syncedAt = :updatedAt " +
            "WHERE date = :date AND packageName = :packageName AND updatedAt = :updatedAt",
    )
    suspend fun markAppUsageSynced(date: String, packageName: String, updatedAt: Long)

    @Query("SELECT * FROM location_pings WHERE id > :afterId ORDER BY id ASC LIMIT :limit")
    suspend fun getPingsAfterId(afterId: Long, limit: Int): List<LocationPing>

    @Query("DELETE FROM daily_device_usage WHERE date < :cutoff")
    suspend fun pruneDeviceUsage(cutoff: String)

    @Query("DELETE FROM daily_app_usage WHERE date < :cutoff")
    suspend fun pruneAppUsage(cutoff: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPing(ping: LocationPing)

    @Query("SELECT * FROM location_pings ORDER BY timestamp DESC LIMIT :limit")
    fun observeRecentPings(limit: Int): Flow<List<LocationPing>>

    @Query("SELECT * FROM location_pings ORDER BY timestamp DESC LIMIT 1")
    suspend fun getLatestPing(): LocationPing?

    @Query("DELETE FROM location_pings WHERE timestamp < :cutoff")
    suspend fun prunePings(cutoff: Long)

    @RawQuery
    suspend fun checkpoint(supportSQLiteQuery: SupportSQLiteQuery): Int
}
