package app.lawnchair.data.usage

import app.lawnchair.util.kotlinxJson
import kotlinx.serialization.Serializable
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST

interface LauncherApi {
    @POST("v1/Autenticacao/Dispositivo")
    suspend fun register(@Body body: LauncherRegisterRequest): LauncherRegisterResponse

    @POST("v1/Launcher/Sync")
    suspend fun sync(
        @Header("Authorization") authorization: String,
        @Body body: LauncherSyncRequest,
    )

    companion object {
        fun create(baseUrl: String): LauncherApi = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(kotlinxJson.asConverterFactory("application/json".toMediaType()))
            .build()
            .create()
    }
}

@Serializable
data class LauncherRegisterRequest(
    val tabletId: String,
    val model: String,
    val registeredAt: Long? = null,
    val bootstrapSecret: String,
    val username: String,
)

@Serializable
data class LauncherRegisterResponse(
    val token: String? = null,
    val error: String? = null,
)

@Serializable
data class LauncherSyncRequest(
    val tabletId: String,
    val device: LauncherDeviceSnapshot,
    val deviceUsages: List<LauncherDeviceUsageDto>,
    val appUsages: List<LauncherAppUsageDto>,
    val locationPings: List<LauncherLocationPingDto>,
)

@Serializable
data class LauncherDeviceSnapshot(
    val model: String,
    val username: String,
    val registeredAt: Long,
    val groupName: String? = null,
    val site: String? = null,
    val siteCode: String? = null,
    val department: String? = null,
    val deviceTag: String? = null,
    val userTag: String? = null,
    val displayName: String? = null,
    val employeeNumber: String? = null,
    val phoneNumber: String? = null,
    val imei: String? = null,
    val iccid: String? = null,
    val carrier: String? = null,
    val deviceName: String? = null,
    val appVersion: String? = null,
    val androidVersion: String? = null,
    val sdkInt: Int? = null,
    val securityPatch: String? = null,
    val manufacturer: String? = null,
    val ramTotalBytes: Long? = null,
    val storageTotalBytes: Long? = null,
    val screenResolution: String? = null,
    val batteryLevel: Int? = null,
    val batteryCharging: Boolean? = null,
    val storageFreeBytes: Long? = null,
    val networkType: String? = null,
)

@Serializable
data class LauncherDeviceUsageDto(
    val date: String,
    val screenOnMs: Long,
)

@Serializable
data class LauncherAppUsageDto(
    val date: String,
    val packageName: String,
    val openCount: Int,
    val foregroundMs: Long,
    val label: String? = null,
)

@Serializable
data class LauncherLocationPingDto(
    val id: String,
    val timestamp: Long,
    val latitude: Double,
    val longitude: Double,
    val accuracyMeters: Float,
    val speedMps: Float? = null,
    val intervalSec: Int,
)
