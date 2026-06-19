package com.cooking.plan

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.cooking.plan.data.settings.AppSettingsRepository
import com.cooking.plan.data.settings.DarkModeSetting
import com.cooking.plan.ui.CookingPlanNavHost
import com.cooking.plan.ui.onboarding.OnboardingScreen
import com.cooking.plan.ui.theme.CookingPlanTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject lateinit var settingsRepository: AppSettingsRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val prefs = getSharedPreferences("cooking_plan", Context.MODE_PRIVATE)
        val isFirstLaunch = !prefs.getBoolean("onboarding_done", false)

        setContent {
            var showOnboarding by remember { mutableStateOf(isFirstLaunch) }
            val settings by settingsRepository.settings.collectAsState()
            val systemDark = isSystemInDarkTheme()
            val darkTheme = when (settings.darkMode) {
                DarkModeSetting.SYSTEM -> systemDark
                DarkModeSetting.LIGHT -> false
                DarkModeSetting.DARK -> true
            }

            CookingPlanTheme(darkTheme = darkTheme) {
                if (showOnboarding) {
                    OnboardingScreen(
                        onFinish = {
                            prefs.edit().putBoolean("onboarding_done", true).apply()
                            showOnboarding = false
                        }
                    )
                } else {
                    CookingPlanNavHost()
                }
            }
        }
    }
}
