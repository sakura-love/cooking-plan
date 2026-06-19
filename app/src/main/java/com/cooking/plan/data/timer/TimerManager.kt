package com.cooking.plan.data.timer

import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.RingtoneManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.os.VibratorManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.asFlow
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

data class TimerInfo(
    val recipeId: Long,
    val stepIndex: Int,
    val stepLabel: String,
    val totalSeconds: Int,
    val remainingSeconds: Int,
    val isRunning: Boolean
) {
    val progress: Float get() = if (totalSeconds > 0) 1f - remainingSeconds.toFloat() / totalSeconds else 0f
}

@Singleton
class TimerManager @Inject constructor(
    @ApplicationContext private val appContext: Context
) {
    private val _activeTimers = MutableStateFlow<Map<Int, TimerInfo>>(emptyMap())
    val activeTimers: StateFlow<Map<Int, TimerInfo>> = _activeTimers.asStateFlow()

    private val timerJobs = mutableMapOf<Int, Job>()
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
    private var mediaPlayer: MediaPlayer? = null

    fun startTimer(recipeId: Long, stepIndex: Int, seconds: Int, stepLabel: String) {
        timerJobs[stepIndex]?.cancel()

        val info = TimerInfo(recipeId, stepIndex, stepLabel, seconds, seconds, true)
        _activeTimers.value = _activeTimers.value + (stepIndex to info)
        ensureServiceRunning()

        timerJobs[stepIndex] = scope.launch {
            var remaining = seconds
            while (remaining > 0 && isActive) {
                delay(1000)
                remaining--
                _activeTimers.value = _activeTimers.value + (stepIndex to info.copy(remainingSeconds = remaining))
            }
            if (remaining == 0 && isActive) {
                _activeTimers.value = _activeTimers.value + (stepIndex to info.copy(remainingSeconds = 0, isRunning = false))
                onTimerFinished(stepLabel)
                stopServiceIfIdle()
            } else {
                _activeTimers.value = _activeTimers.value - stepIndex
                stopServiceIfIdle()
            }
        }
    }

    fun stopTimer(stepIndex: Int) {
        timerJobs[stepIndex]?.cancel()
        timerJobs.remove(stepIndex)
        _activeTimers.value = _activeTimers.value - stepIndex
        stopServiceIfIdle()
    }

    fun stopAll() {
        timerJobs.values.forEach { it.cancel() }
        timerJobs.clear()
        _activeTimers.value = emptyMap()
        mediaPlayer?.release()
        mediaPlayer = null
        appContext.stopService(Intent(appContext, TimerService::class.java))
    }

    private fun ensureServiceRunning() {
        ContextCompat.startForegroundService(appContext, Intent(appContext, TimerService::class.java))
    }

    private fun stopServiceIfIdle() {
        if (_activeTimers.value.isEmpty()) {
            appContext.stopService(Intent(appContext, TimerService::class.java))
        }
    }

    private fun onTimerFinished(stepLabel: String) {
        try {
            mediaPlayer?.release()
            val uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            mediaPlayer = MediaPlayer().apply {
                setDataSource(appContext, uri)
                setAudioAttributes(
                    AudioAttributes.Builder()
                        .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                        .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                        .build()
                )
                isLooping = false
                setOnCompletionListener { mp ->
                    mp.release()
                    if (mediaPlayer === mp) mediaPlayer = null
                }
                prepare()
                start()
            }
        } catch (_: Exception) {}

        try {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
                val vm = appContext.getSystemService(VibratorManager::class.java)
                vm?.defaultVibrator?.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
            } else {
                @Suppress("DEPRECATION")
                val vibrator = appContext.getSystemService(Context.VIBRATOR_SERVICE) as? Vibrator
                vibrator?.vibrate(VibrationEffect.createOneShot(300, VibrationEffect.DEFAULT_AMPLITUDE))
            }
        } catch (_: Exception) {}
    }
}
