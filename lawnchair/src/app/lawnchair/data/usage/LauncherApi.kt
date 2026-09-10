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
    ): LauncherSyncResponse

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
    val tabletId: String? = null,
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
    val intervalMin: Int,
)

@Serializable
data class LauncherSyncCount(
    val accepted: Int = 0,
    val duplicates: Int = 0,
)

@Serializable
data class LauncherSyncResponse(
    val deviceUpserted: Boolean = false,
    val deviceUsages: LauncherSyncCount = LauncherSyncCount(),
    val appUsages: LauncherSyncCount = LauncherSyncCount(),
    val locationPings: LauncherSyncCount = LauncherSyncCount(),
    val errors: List<String> = emptyList(),
)
