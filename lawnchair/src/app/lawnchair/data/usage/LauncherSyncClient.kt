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
        return try {
            val serial = DeviceSerial.resolve(context) ?: return false
            val baseUrl = LauncherApiConfig.baseUrl(context) ?: return false
            val secret = LauncherApiConfig.bootstrapSecret(context) ?: return false
            val username = LauncherApiConfig.username(context) ?: return false
            val identity = dao.getIdentity() ?: return false
            val store = LauncherAuthStore(context)
            val api = LauncherApi.create(baseUrl)
            val token = ensureToken(api, store, identity, secret, username) ?: return false
            pushBatches(api, store, dao, identity, serial, token, secret, username)
        } catch (_: Exception) {
            false
        }
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
        val token = response.token?.takeIf { it.isNotBlank() } ?: return null
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
        var usageOffset = 0
        var appOffset = 0
        var usagesDone = false
        var appsDone = false
        var retriedAuth = false
        val labels = dao.getEnabledWatched().associate { it.packageName to it.label }

        repeat(MAX_BATCHES) {
            val deviceUsages = if (usagesDone) {
                emptyList()
            } else {
                dao.getDeviceUsagesPage(DEVICE_USAGE_BATCH, usageOffset)
            }
            val appUsages = if (appsDone) {
                emptyList()
            } else {
                dao.getAppUsagesPage(APP_USAGE_BATCH, appOffset)
            }
            val pings = dao.getPingsAfterId(pingAfter, PING_BATCH)
            if (deviceUsages.isEmpty() && appUsages.isEmpty() && pings.isEmpty()) {
                store.markSynced(pingAfter)
                return true
            }

            val body = LauncherSyncRequest(
                tabletId = serial,
                device = LauncherDeviceSnapshot(
                    model = identity.model,
                    username = username,
                    registeredAt = identity.registeredAt,
                ),
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
                        intervalMin = ping.intervalMin,
                    )
                },
            )

            try {
                api.sync("Bearer $currentToken", body)
            } catch (ex: HttpException) {
                if (!retriedAuth && ex.code() == 401) {
                    store.clearToken()
                    currentToken = register(api, store, identity, secret, username) ?: return false
                    retriedAuth = true
                    api.sync("Bearer $currentToken", body)
                } else {
                    return false
                }
            }

            if (pings.isNotEmpty()) {
                pingAfter = pings.maxOf { it.id }
            }
            store.markSynced(pingAfter)
            usageOffset += deviceUsages.size
            appOffset += appUsages.size
            usagesDone = deviceUsages.size < DEVICE_USAGE_BATCH
            appsDone = appUsages.size < APP_USAGE_BATCH
            if (usagesDone && appsDone && pings.size < PING_BATCH) {
                return true
            }
        }
        return true
    }

    private fun pingClientId(tabletId: String, localId: Long): String {
        return UUID.nameUUIDFromBytes("$tabletId:$localId".toByteArray()).toString()
    }
}
