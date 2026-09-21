package app.lawnchair.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.SupportSQLiteDatabase
import app.lawnchair.data.folder.FolderInfoEntity
import app.lawnchair.data.folder.FolderItemEntity
import app.lawnchair.data.folder.service.FolderDao
import app.lawnchair.data.iconoverride.IconOverride
import app.lawnchair.data.iconoverride.IconOverrideDao
import app.lawnchair.data.usage.DailyAppUsage
import app.lawnchair.data.usage.DailyDeviceUsage
import app.lawnchair.data.usage.DeviceIdentity
import app.lawnchair.data.usage.LocationPing
import app.lawnchair.data.usage.UsageDao
import app.lawnchair.data.usage.WatchedApp
import app.lawnchair.data.wallpaper.Wallpaper
import app.lawnchair.data.wallpaper.service.WallpaperDao
import app.lawnchair.util.MainThreadInitializedObject
import kotlinx.coroutines.runBlocking

@Database(
    entities = [
        IconOverride::class,
        Wallpaper::class,
        FolderInfoEntity::class,
        FolderItemEntity::class,
        DeviceIdentity::class,
        WatchedApp::class,
        DailyDeviceUsage::class,
        DailyAppUsage::class,
        LocationPing::class,
    ],
    version = 8,
)
@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun iconOverrideDao(): IconOverrideDao
    abstract fun wallpaperDao(): WallpaperDao
    abstract fun folderDao(): FolderDao
    abstract fun usageDao(): UsageDao

    suspend fun checkpoint() {
        iconOverrideDao().checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
        wallpaperDao().checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
        folderDao().checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
        usageDao().checkpoint(SimpleSQLiteQuery("pragma wal_checkpoint(full)"))
    }

    fun checkpointSync() {
        runBlocking {
            checkpoint()
        }
    }

    companion object {
        val MIGRATION_1_3 = object : Migration(1, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `Wallpapers` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `imagePath` TEXT NOT NULL,
                `rank` INTEGER NOT NULL,
                `timestamp` INTEGER NOT NULL,
                `checksum` TEXT
            )
                    """.trimIndent(),
                )

                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `Folders` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `title` TEXT NOT NULL,
                `hide` INTEGER NOT NULL,
                `rank` INTEGER NOT NULL,
                `timestamp` INTEGER NOT NULL
            )
                    """.trimIndent(),
                )

                database.execSQL(
                    """
            CREATE TABLE IF NOT EXISTS `FolderItems` (
                `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                `folderId` INTEGER NOT NULL,
                `rank` INTEGER NOT NULL,
                `item_info` TEXT,
                `timestamp` INTEGER NOT NULL,
                FOREIGN KEY(`folderId`) REFERENCES `Folders`(`id`) ON UPDATE CASCADE ON DELETE CASCADE
            )
                    """.trimIndent(),
                )

                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_FolderItems_folderId` ON `FolderItems` (`folderId`)",
                )
            }
        }

        val MIGRATION_2_3 = object : Migration(2, 3) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("CREATE INDEX IF NOT EXISTS index_FolderItems_folderId ON FolderItems(folderId)")
            }
        }

        val MIGRATION_4_5 = object : Migration(4, 5) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `location_pings` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `latitude` REAL NOT NULL,
                        `longitude` REAL NOT NULL,
                        `accuracyMeters` REAL NOT NULL,
                        `speedMps` REAL,
                        `intervalMin` INTEGER NOT NULL
                    )
                    """.trimIndent(),
                )
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_location_pings_timestamp` ON `location_pings` (`timestamp`)",
                )
            }
        }

        val MIGRATION_3_4 = object : Migration(3, 4) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `device_identity` (
                        `id` INTEGER NOT NULL,
                        `tabletId` TEXT NOT NULL,
                        `model` TEXT NOT NULL,
                        `registeredAt` INTEGER NOT NULL,
                        PRIMARY KEY(`id`)
                    )
                    """.trimIndent(),
                )
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `watched_apps` (
                        `packageName` TEXT NOT NULL,
                        `label` TEXT NOT NULL,
                        `enabled` INTEGER NOT NULL,
                        PRIMARY KEY(`packageName`)
                    )
                    """.trimIndent(),
                )
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `daily_device_usage` (
                        `date` TEXT NOT NULL,
                        `screenOnMs` INTEGER NOT NULL,
                        PRIMARY KEY(`date`)
                    )
                    """.trimIndent(),
                )
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `daily_app_usage` (
                        `date` TEXT NOT NULL,
                        `packageName` TEXT NOT NULL,
                        `openCount` INTEGER NOT NULL,
                        `foregroundMs` INTEGER NOT NULL,
                        PRIMARY KEY(`date`, `packageName`)
                    )
                    """.trimIndent(),
                )
            }
        }

        val MIGRATION_5_6 = object : Migration(5, 6) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `location_pings_new` (
                        `id` INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                        `timestamp` INTEGER NOT NULL,
                        `latitude` REAL NOT NULL,
                        `longitude` REAL NOT NULL,
                        `accuracyMeters` REAL NOT NULL,
                        `speedMps` REAL,
                        `intervalSec` INTEGER NOT NULL
                    )
                    """.trimIndent(),
                )
                database.execSQL(
                    """
                    INSERT INTO `location_pings_new` (
                        `id`, `timestamp`, `latitude`, `longitude`, `accuracyMeters`, `speedMps`, `intervalSec`
                    )
                    SELECT `id`, `timestamp`, `latitude`, `longitude`, `accuracyMeters`, `speedMps`, `intervalMin` * 60
                    FROM `location_pings`
                    """.trimIndent(),
                )
                database.execSQL("DROP TABLE `location_pings`")
                database.execSQL("ALTER TABLE `location_pings_new` RENAME TO `location_pings`")
                database.execSQL(
                    "CREATE INDEX IF NOT EXISTS `index_location_pings_timestamp` ON `location_pings` (`timestamp`)",
                )

                database.execSQL(
                    "ALTER TABLE `daily_device_usage` ADD COLUMN `updatedAt` INTEGER NOT NULL DEFAULT 0",
                )
                database.execSQL(
                    "ALTER TABLE `daily_device_usage` ADD COLUMN `syncedAt` INTEGER NOT NULL DEFAULT 0",
                )
                database.execSQL("UPDATE `daily_device_usage` SET `updatedAt` = 1")

                database.execSQL(
                    "ALTER TABLE `daily_app_usage` ADD COLUMN `updatedAt` INTEGER NOT NULL DEFAULT 0",
                )
                database.execSQL(
                    "ALTER TABLE `daily_app_usage` ADD COLUMN `syncedAt` INTEGER NOT NULL DEFAULT 0",
                )
                database.execSQL("UPDATE `daily_app_usage` SET `updatedAt` = 1")
            }
        }

        val MIGRATION_6_7 = object : Migration(6, 7) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS `watched_apps_new` (
                        `packageName` TEXT NOT NULL,
                        `label` TEXT NOT NULL,
                        PRIMARY KEY(`packageName`)
                    )
                    """.trimIndent(),
                )
                database.execSQL(
                    """
                    INSERT INTO `watched_apps_new` (`packageName`, `label`)
                    SELECT `packageName`, `label` FROM `watched_apps` WHERE `enabled` = 1
                    """.trimIndent(),
                )
                database.execSQL("DROP TABLE `watched_apps`")
                database.execSQL("ALTER TABLE `watched_apps_new` RENAME TO `watched_apps`")
            }
        }

        val MIGRATION_7_8 = object : Migration(7, 8) {
            override fun migrate(database: SupportSQLiteDatabase) {
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `groupName` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `site` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `siteCode` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `department` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `deviceTag` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `userTag` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `displayName` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `employeeNumber` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `phoneNumber` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `imei` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `iccid` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `carrier` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `deviceName` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `appVersion` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `androidVersion` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `sdkInt` INTEGER")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `securityPatch` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `manufacturer` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `ramTotalBytes` INTEGER")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `storageTotalBytes` INTEGER")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `screenResolution` TEXT")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `batteryLevel` INTEGER")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `batteryCharging` INTEGER")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `storageFreeBytes` INTEGER")
                database.execSQL("ALTER TABLE `device_identity` ADD COLUMN `networkType` TEXT")
            }
        }

        val INSTANCE = MainThreadInitializedObject { context ->
            Room.databaseBuilder(
                context,
                AppDatabase::class.java,
                "preferences",
            ).addMigrations(MIGRATION_1_3)
                .addMigrations(MIGRATION_2_3)
                .addMigrations(MIGRATION_3_4)
                .addMigrations(MIGRATION_4_5)
                .addMigrations(MIGRATION_5_6)
                .addMigrations(MIGRATION_6_7)
                .addMigrations(MIGRATION_7_8)
                .build()
        }
    }
}
