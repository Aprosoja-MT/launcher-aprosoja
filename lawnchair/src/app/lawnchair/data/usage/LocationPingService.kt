package app.lawnchair.data.usage

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.location.Location
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
import java.util.concurrent.TimeUnit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch

class LocationPingService : Service() {
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.IO)
    private var activeMode: PingMode? = null
    private var collectJob: Job? = null
    private var tracker: MovementTracker? = null
    private val sampler = TrackSampler()
    private val callback = object : LocationCallback() {
        override fun onLocationResult(result: LocationResult) {
            if (!isEligible()) {
                stopUpdates()
                stopSelf()
                return
            }
            val location = result.lastLocation ?: return
            if (!LocationFix.isRecordable(location)) return
            val movement = tracker ?: return
            val previous = movement.mode
            movement.onLocation(location)
            if (movement.mode != previous) return
            if (!sampler.shouldRecord(location, previous)) return
            record(location, previous)
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
            .setContentTitle(getString(R.string.usage_audit_ping_notification))
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
        if (!isEligible()) {
            stopUpdates()
            stopSelf()
            return START_NOT_STICKY
        }
        val movement = tracker ?: MovementTracker(this, scope, ::onModeChanged).also {
            tracker = it
            it.start()
        }
        startUpdates(movement.mode)
        startCollectLoop()
        return START_STICKY
    }

    private fun isEligible(): Boolean {
        return LocationPermission.hasFine(this) && DeviceSerial.resolve(this) != null
    }

    private fun onModeChanged(mode: PingMode, location: Location) {
        startUpdates(mode)
        record(location, mode)
    }

    private fun record(location: Location, mode: PingMode) {
        sampler.accept(location)
        scope.launch {
            val dao = AppDatabase.INSTANCE.get(this@LocationPingService).usageDao()
            PingCollector.collectLocation(this@LocationPingService, dao, location, mode)
        }
    }

    @SuppressLint("MissingPermission")
    private fun startUpdates(mode: PingMode) {
        if (activeMode == mode) return
        val client = LocationServices.getFusedLocationProviderClient(this)
        client.removeLocationUpdates(callback)
        val priority = when (mode) {
            PingMode.IDLE -> Priority.PRIORITY_BALANCED_POWER_ACCURACY
            PingMode.MOVING -> Priority.PRIORITY_HIGH_ACCURACY
        }
        val request = LocationRequest.Builder(priority, mode.sampleIntervalMs)
            .setMinUpdateIntervalMillis(mode.sampleIntervalMs)
            .setMaxUpdateDelayMillis(0)
            .setWaitForAccurateLocation(mode == PingMode.MOVING)
            .build()
        try {
            client.requestLocationUpdates(request, callback, Looper.getMainLooper())
            activeMode = mode
        } catch (_: Exception) {
            activeMode = null
            stopSelf()
        }
    }

    private fun startCollectLoop() {
        if (collectJob?.isActive == true) return
        collectJob = scope.launch {
            val service = UsageService.INSTANCE.get(this@LocationPingService)
            while (isActive) {
                service.collectToday()
                delay(TimeUnit.MINUTES.toMillis(PingRules.COLLECT_MINUTES))
            }
        }
    }

    private fun stopUpdates() {
        activeMode = null
        sampler.reset()
        collectJob?.cancel()
        collectJob = null
        tracker?.stop()
        tracker = null
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

        fun start(context: Context) {
            val app = context.applicationContext
            try {
                ContextCompat.startForegroundService(app, Intent(app, LocationPingService::class.java))
            } catch (_: Exception) {
            }
        }

        fun stop(context: Context) {
            val app = context.applicationContext
            app.stopService(Intent(app, LocationPingService::class.java))
        }
    }
}
