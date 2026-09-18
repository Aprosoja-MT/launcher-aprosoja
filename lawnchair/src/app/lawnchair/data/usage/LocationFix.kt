package app.lawnchair.data.usage

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import android.location.LocationManager
import androidx.core.content.getSystemService
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.Tasks
import java.util.concurrent.TimeUnit

object LocationFix {
    private const val TIMEOUT_SEC = 15L
    private const val LAST_KNOWN_MAX_AGE_MS = 5 * 60 * 1000L

    @SuppressLint("MissingPermission")
    fun resolve(context: Context): Location? {
        val fresh = fusedCurrent(context)
        if (fresh != null && isRecordable(fresh)) return fresh
        val last = lastKnown(context) ?: return null
        if (!isRecordable(last)) return null
        val age = System.currentTimeMillis() - last.time
        if (age > LAST_KNOWN_MAX_AGE_MS) return null
        return last
    }

    fun isValid(location: Location): Boolean {
        if (!location.latitude.isFinite() || !location.longitude.isFinite()) return false
        return location.latitude != 0.0 || location.longitude != 0.0
    }

    fun isRecordable(location: Location): Boolean {
        if (!isValid(location)) return false
        return !location.hasAccuracy() || location.accuracy <= PingRules.MAX_ACCURACY_M
    }

    fun timestampOf(location: Location): Long {
        return if (location.time > 0L) location.time else System.currentTimeMillis()
    }

    @SuppressLint("MissingPermission")
    private fun lastKnown(context: Context): Location? {
        return fusedLast(context) ?: managerLast(context)
    }

    @SuppressLint("MissingPermission")
    private fun fusedCurrent(context: Context): Location? {
        return try {
            val client = LocationServices.getFusedLocationProviderClient(context)
            val token = CancellationTokenSource()
            Tasks.await(
                client.getCurrentLocation(Priority.PRIORITY_HIGH_ACCURACY, token.token),
                TIMEOUT_SEC,
                TimeUnit.SECONDS,
            )
        } catch (_: Exception) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    private fun fusedLast(context: Context): Location? {
        return try {
            val client = LocationServices.getFusedLocationProviderClient(context)
            Tasks.await(client.lastLocation, TIMEOUT_SEC, TimeUnit.SECONDS)
        } catch (_: Exception) {
            null
        }
    }

    @SuppressLint("MissingPermission")
    private fun managerLast(context: Context): Location? {
        val manager = context.getSystemService<LocationManager>() ?: return null
        val gps = try {
            manager.getLastKnownLocation(LocationManager.GPS_PROVIDER)
        } catch (_: Exception) {
            null
        }
        val network = try {
            manager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        } catch (_: Exception) {
            null
        }
        return listOfNotNull(gps, network).maxByOrNull { it.time }
    }
}
