package app.lawnchair.data.usage

import android.content.Context
import java.util.UUID
import retrofit2.HttpException

object LauncherSyncClient {
    private const val DEVICE_USAGE_BATCH = 100
    private const val APP_USAGE_BATCH = 200
    private const val PING_BATCH = 500
    private const val MAX_BATCHES = 20

    suspend fun sync(context: Context, dao: UsageDao): Boolean {
        val store = LauncherAuthStore(context)
        return try {
            val serial = DeviceSerial.resolve(context) ?: return store.reject("serial unavailable")
            val baseUrl = LauncherApiConfig.baseUrl(context) ?: return store.reject("API URL unavailable")
            val secret = LauncherApiConfig.bootstrapSecret(context) ?: return store.reject("bootstrap secret unavailable")
            val username = LauncherApiConfig.username(context) ?: return store.reject("username unavailable")
            val identity = dao.getIdentity() ?: return store.reject("device identity not collected yet")
            val api = LauncherApi.create(baseUrl)
            val token = ensureToken(api, store, identity, secret, username) ?: return false
            val synced = pushBatches(api, store, dao, identity, serial, token, secret, username)
            if (synced) {
                store.clearError()
            }
            synced
        } catch (ex: Exception) {
            store.saveError("${ex.javaClass.simpleName}: ${ex.message.orEmpty()}")
            false
        }
    }

    private fun LauncherAuthStore.reject(reason: String): Boolean {
        saveError(reason)
        return false
    }

    private suspend fun ensureToken(
        api: LauncherApi,
        store: LauncherAuthStore,
        identity: DeviceIdentity,
        secret: String,
        username: String,
    ): String? {
        val stored = store.token()
        if (!stored.isNullOrBlank() && store.tabletId() == identity.tabletId) {
            return stored
        }
        return register(api, store, identity, secret, username)
    }

    private suspend fun register(
        api: LauncherApi,
        store: LauncherAuthStore,
        identity: DeviceIdentity,
        secret: String,
        username: String,
    ): String? {
        val response = api.register(
            LauncherRegisterRequest(
                tabletId = identity.tabletId,
                model = identity.model,
                registeredAt = identity.registeredAt,
                bootstrapSecret = secret,
                username = username,
            ),
        )
        val token = response.token?.takeIf { it.isNotBlank() }
        if (token == null) {
            store.saveError(response.error ?: "register: response without token")
            return null
        }
        store.saveToken(identity.tabletId, token)
        return token
    }

    private suspend fun pushBatches(
        api: LauncherApi,
        store: LauncherAuthStore,
        dao: UsageDao,
        identity: DeviceIdentity,
        serial: String,
        token: String,
        secret: String,
        username: String,
    ): Boolean {
        var currentToken = token
        var pingAfter = store.lastSyncedPingId()
        var retriedAuth = false
        val labels = dao.getWatched().associate { it.packageName to it.label }
        val identityHash = identity.hashCode()

        repeat(MAX_BATCHES) {
            val deviceUsages = dao.getPendingDeviceUsages(DEVICE_USAGE_BATCH)
            val appUsages = dao.getPendingAppUsages(APP_USAGE_BATCH)
            val pings = dao.getPingsAfterId(pingAfter, PING_BATCH)
            if (deviceUsages.isEmpty() &&
                appUsages.isEmpty() &&
                pings.isEmpty() &&
                store.syncedIdentityHash() == identityHash
            ) {
                store.markSynced(pingAfter)
                return true
            }

            val body = LauncherSyncRequest(
                tabletId = serial,
                device = deviceSnapshot(identity, username),
                deviceUsages = deviceUsages.map {
                    LauncherDeviceUsageDto(date = it.date, screenOnMs = it.screenOnMs)
                },
                appUsages = appUsages.map { usage ->
                    LauncherAppUsageDto(
                        date = usage.date,
                        packageName = usage.packageName,
                        openCount = usage.openCount,
                        foregroundMs = usage.foregroundMs,
                        label = labels[usage.packageName],
                    )
                },
                locationPings = pings.map { ping ->
                    LauncherLocationPingDto(
                        id = pingClientId(serial, ping.id),
                        timestamp = ping.timestamp,
                        latitude = ping.latitude,
                        longitude = ping.longitude,
                        accuracyMeters = ping.accuracyMeters,
                        speedMps = ping.speedMps,
                        intervalSec = ping.intervalSec,
                    )
                },
            )

            try {
                api.sync("Bearer $currentToken", body)
            } catch (ex: HttpException) {
                if (retriedAuth || ex.code() != 401) {
                    return store.reject("sync: HTTP ${ex.code()}")
                }
                store.clearToken()
                currentToken = register(api, store, identity, secret, username) ?: return false
                retriedAuth = true
                try {
                    api.sync("Bearer $currentToken", body)
                } catch (retry: HttpException) {
                    return store.reject("sync: HTTP ${retry.code()}")
                }
            }

            store.markIdentitySynced(identityHash)
            deviceUsages.forEach { dao.markDeviceUsageSynced(it.date, it.updatedAt) }
            appUsages.forEach { dao.markAppUsageSynced(it.date, it.packageName, it.updatedAt) }
            if (pings.isNotEmpty()) {
                pingAfter = pings.maxOf { it.id }
            }
            store.markSynced(pingAfter)
            if (deviceUsages.size < DEVICE_USAGE_BATCH &&
                appUsages.size < APP_USAGE_BATCH &&
                pings.size < PING_BATCH
            ) {
                return true
            }
        }
        return true
    }

    private fun deviceSnapshot(identity: DeviceIdentity, username: String): LauncherDeviceSnapshot {
        val knox = identity.knox
        val specs = identity.specs
        return LauncherDeviceSnapshot(
            model = identity.model,
            username = username,
            registeredAt = identity.registeredAt,
            groupName = knox.groupName,
            deviceTag = knox.deviceTag,
            userTag = knox.userTag,
            employeeNumber = knox.employeeNumber,
            phoneNumber = knox.phoneNumber,
            imei = knox.imei,
            iccid = knox.iccid,
            carrier = knox.carrier,
            deviceName = knox.deviceName,
            appVersion = specs.appVersion,
            androidVersion = specs.androidVersion,
            sdkInt = specs.sdkInt,
            securityPatch = specs.securityPatch,
            manufacturer = specs.manufacturer,
            ramTotalBytes = specs.ramTotalBytes,
            storageTotalBytes = specs.storageTotalBytes,
            screenResolution = specs.screenResolution,
            batteryLevel = specs.batteryLevel,
            batteryCharging = specs.batteryCharging,
            storageFreeBytes = specs.storageFreeBytes,
            networkType = specs.networkType,
        )
    }

    private fun pingClientId(tabletId: String, localId: Long): String {
        return UUID.nameUUIDFromBytes("$tabletId:$localId".toByteArray()).toString()
    }
}
