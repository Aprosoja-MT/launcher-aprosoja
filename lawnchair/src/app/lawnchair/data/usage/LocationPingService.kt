package app.lawnchair.data.usage

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.IBinder
import android.os.Looper
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import app.lawnchair.data.AppDatabase
import app.lawnchair.util.requireSystemService
import com.android.launcher3.R
import com.android.launcher3.Utilities
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

class LocationPingService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var activeIntervalMin: Int? = null
    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            val intervalMin = PingInterval.resolve(this@LocationPingService)
            if (!PingInterval.isRoute(intervalMin) ||
                !LocationPermission.hasFine(this@LocationPingService) ||
                DeviceSerial.resolve(this@LocationPingService) == null
            ) {
                stopUpdates()
                stopSelf()
                return
            }
            if (activeIntervalMin != intervalMin) {
                startUpdates(intervalMin)
                return
            }
            val location = result.lastLocation ?: return
            val dao = AppDatabase.INSTANCE.get(this@LocationPingService).usageDao()
            scope.launch {
                PingCollector.collectLocation(this@LocationPingService, dao, location)
            }
        }
    }

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onCreate() {
        super.onCreate()
        val notificationManager: NotificationManager = requireSystemService()
        notificationManager.createNotificationChannel(
            NotificationChannel(
                CHANNEL_ID,
                getString(R.string.usage_audit_ping_channel),
                NotificationManager.IMPORTANCE_LOW,
            ),
        )
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_menu_mylocation)
            .setContentTitle(getString(R.string.usage_audit_ping_route_notification))
            .setOngoing(true)
            .setOnlyAlertOnce(true)
            .build()
        if (Utilities.ATLEAST_U) {
            startForeground(NOTIFICATION_ID, notification, ServiceInfo.FOREGROUND_SERVICE_TYPE_LOCATION)
        } else {
            startForeground(NOTIFICATION_ID, notification)
        }
    }

    @SuppressLint("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        if (intent?.action == ACTION_STOP) {
            stopUpdates()
            stopSelf()
            return START_NOT_STICKY
        }
        val intervalMin = PingInterval.resolve(this)
        if (!PingInterval.isRoute(intervalMin) ||
            !LocationPermission.hasFine(this) ||
            DeviceSerial.resolve(this) == null
        ) {
            stopUpdates()
            stopSelf()
            return START_NOT_STICKY
        }
        startUpdates(intervalMin)
        return START_STICKY
    }

    @SuppressLint("MissingPermission")
    private fun startUpdates(intervalMin: Int) {
        if (activeIntervalMin == intervalMin) return
        val client = LocationServices.getFusedLocationProviderClient(this)
        client.removeLocationUpdates(callback)
        val intervalMs = intervalMin * 60_000L
        val request = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, intervalMs)
            .setMinUpdateIntervalMillis(intervalMs)
            .build()
        try {
            client.requestLocationUpdates(request, callback, Looper.getMainLooper())
            activeIntervalMin = intervalMin
        } catch (_: Exception) {
            activeIntervalMin = null
            stopSelf()
        }
    }

    private fun stopUpdates() {
        activeIntervalMin = null
        try {
            LocationServices.getFusedLocationProviderClient(this).removeLocationUpdates(callback)
        } catch (_: Exception) {
        }
    }

    override fun onDestroy() {
        stopUpdates()
        scope.cancel()
        super.onDestroy()
    }

    companion object {
        private const val CHANNEL_ID = "location_ping"
        private const val NOTIFICATION_ID = 2101
        private const val ACTION_STOP = "app.lawnchair.data.usage.STOP_LOCATION_PING"

        fun start(context: Context) {
            val app = context.applicationContext
            try {
                ContextCompat.startForegroundService(app, Intent(app, LocationPingService::class.java))
            } catch (_: Exception) {
            }
        }

        fun stop(context: Context) {
            val app = context.applicationContext
            app.stopService(Intent(app, LocationPingService::class.java).setAction(ACTION_STOP))
        }
    }
}
