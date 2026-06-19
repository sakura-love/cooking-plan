package com.cooking.plan.data.settings

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

enum class DarkModeSetting { SYSTEM, LIGHT, DARK }

enum class AiProvider(val displayName: String, val defaultBaseUrl: String, val defaultModel: String) {
    DEEPSEEK("DeepSeek", "https://api.deepseek.com", "deepseek-v4-flash"),
    ZHIPU("智谱", "https://open.bigmodel.cn/api/paas/v4", "glm-4.7-flash"),
    OPENAI("OpenAI", "https://api.openai.com/v1", "gpt-4o-mini"),
    CUSTOM("自定义", "", "")
}

data class AppSettings(
    val darkMode: DarkModeSetting = DarkModeSetting.SYSTEM,
    val aiProvider: AiProvider = AiProvider.DEEPSEEK,
    val aiApiKey: String = "",
    val aiBaseUrl: String = AiProvider.DEEPSEEK.defaultBaseUrl,
    val aiModel: String = AiProvider.DEEPSEEK.defaultModel,
    val activeCookingRecipeId: Long = 0
)

@Singleton
class AppSettingsRepository @Inject constructor(
    @ApplicationContext context: Context
) {
    private val prefs: SharedPreferences = context.getSharedPreferences("cooking_plan", Context.MODE_PRIVATE)

    private val _settings = MutableStateFlow(readSettings())
    val settings: StateFlow<AppSettings> = _settings.asStateFlow()

    fun setDarkMode(mode: DarkModeSetting) {
        edit { putString(KEY_DARK_MODE, mode.name) }
    }

    fun setAiProvider(provider: AiProvider) {
        val current = _settings.value
        val shouldReplaceEndpoint = current.aiBaseUrl.isBlank() ||
            current.aiBaseUrl == current.aiProvider.defaultBaseUrl
        val shouldReplaceModel = current.aiModel.isBlank() ||
            current.aiModel == current.aiProvider.defaultModel

        edit {
            putString(KEY_AI_PROVIDER, provider.name)
            if (shouldReplaceEndpoint) putString(KEY_AI_BASE_URL, provider.defaultBaseUrl)
            if (shouldReplaceModel) putString(KEY_AI_MODEL, provider.defaultModel)
        }
    }

    fun setAiApiKey(apiKey: String) {
        edit { putString(KEY_AI_API_KEY, apiKey) }
    }

    fun setAiBaseUrl(baseUrl: String) {
        edit { putString(KEY_AI_BASE_URL, baseUrl.trim()) }
    }

    fun setAiModel(model: String) {
        edit { putString(KEY_AI_MODEL, model.trim()) }
    }

    fun setActiveCookingRecipe(recipeId: Long) {
        edit { putLong(KEY_ACTIVE_COOKING_RECIPE_ID, recipeId) }
    }

    fun clearActiveCookingRecipe(recipeId: Long? = null) {
        if (recipeId != null && _settings.value.activeCookingRecipeId != recipeId) return
        edit { putLong(KEY_ACTIVE_COOKING_RECIPE_ID, 0) }
    }

    private fun edit(block: SharedPreferences.Editor.() -> Unit) {
        prefs.edit().apply(block).apply()
        _settings.value = readSettings()
    }

    private fun readSettings(): AppSettings {
        val provider = runCatching {
            AiProvider.valueOf(prefs.getString(KEY_AI_PROVIDER, AiProvider.DEEPSEEK.name) ?: AiProvider.DEEPSEEK.name)
        }.getOrDefault(AiProvider.DEEPSEEK)
        val darkMode = runCatching {
            DarkModeSetting.valueOf(prefs.getString(KEY_DARK_MODE, DarkModeSetting.SYSTEM.name) ?: DarkModeSetting.SYSTEM.name)
        }.getOrDefault(DarkModeSetting.SYSTEM)

        return AppSettings(
            darkMode = darkMode,
            aiProvider = provider,
            aiApiKey = prefs.getString(KEY_AI_API_KEY, "") ?: "",
            aiBaseUrl = prefs.getString(KEY_AI_BASE_URL, provider.defaultBaseUrl) ?: provider.defaultBaseUrl,
            aiModel = prefs.getString(KEY_AI_MODEL, provider.defaultModel) ?: provider.defaultModel,
            activeCookingRecipeId = prefs.getLong(KEY_ACTIVE_COOKING_RECIPE_ID, 0)
        )
    }

    private companion object {
        const val KEY_DARK_MODE = "dark_mode"
        const val KEY_AI_PROVIDER = "ai_provider"
        const val KEY_AI_API_KEY = "ai_api_key"
        const val KEY_AI_BASE_URL = "ai_base_url"
        const val KEY_AI_MODEL = "ai_model"
        const val KEY_ACTIVE_COOKING_RECIPE_ID = "active_cooking_recipe_id"
    }
}
