package com.cooking.plan.data.ai

import com.cooking.plan.data.local.IngredientEntity
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.data.local.StepEntity
import com.cooking.plan.data.settings.AppSettingsRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

data class GeneratedRecipe(
    val recipe: RecipeEntity,
    val ingredients: List<IngredientEntity>,
    val steps: List<StepEntity>
)

@Serializable
private data class ChatRequest(
    val model: String,
    val messages: List<ChatMessage>,
    val temperature: Double = 0.7
)

@Serializable
private data class ChatMessage(
    val role: String,
    val content: String
)

@Serializable
private data class ChatResponse(
    val choices: List<ChatChoice> = emptyList()
)

@Serializable
private data class ChatChoice(
    val message: ChatMessageContent? = null
)

@Serializable
private data class ChatMessageContent(
    val content: String = ""
)

@Serializable
private data class AiRecipePayload(
    val name: String,
    val category: String,
    val description: String = "",
    val servings: Int = 2,
    @SerialName("totalTime")
    val totalTime: Int = 30,
    val ingredients: List<AiIngredientPayload> = emptyList(),
    val steps: List<AiStepPayload> = emptyList()
)

@Serializable
private data class AiIngredientPayload(
    val name: String,
    val quantity: String = "",
    val unit: String = ""
)

@Serializable
private data class AiStepPayload(
    val description: String,
    val timerSeconds: Int? = null,
    val timerType: String? = null
)

@Singleton
class AiRecipeService @Inject constructor(
    private val settingsRepository: AppSettingsRepository
) {
    private val json = Json {
        ignoreUnknownKeys = true
        encodeDefaults = true
    }

    private val client = OkHttpClient.Builder()
        .connectTimeout(20, TimeUnit.SECONDS)
        .readTimeout(60, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .retryOnConnectionFailure(true)
        .build()

    private val jsonMediaType = "application/json; charset=utf-8".toMediaType()

    suspend fun generateRecipe(recipeName: String): Result<GeneratedRecipe> = withContext(Dispatchers.IO) {
        runCatching {
            val settings = settingsRepository.settings.value
            require(settings.aiApiKey.isNotBlank()) { "请先在设置中填写 AI API Key" }
            require(settings.aiBaseUrl.isNotBlank()) { "请先在设置中填写 API 地址" }
            require(settings.aiModel.isNotBlank()) { "请先在设置中填写模型名称" }

            val requestBody = json.encodeToString(
                ChatRequest.serializer(),
                ChatRequest(
                    model = settings.aiModel,
                    messages = listOf(
                        ChatMessage(
                            role = "system",
                            content = "你是料理计划 App 的菜谱生成器。只输出一个 JSON 对象，不要 Markdown，不要解释。"
                        ),
                        ChatMessage(role = "user", content = buildPrompt(recipeName))
                    )
                )
            )

            val endpoint = settings.aiBaseUrl.trimEnd('/') + "/chat/completions"
            val request = Request.Builder()
                .url(endpoint)
                .post(requestBody.toRequestBody(jsonMediaType))
                .header("Authorization", "Bearer ${settings.aiApiKey}")
                .build()

            val response = client.newCall(request).execute()
            val responseText = if (response.isSuccessful) {
                response.body?.string().orEmpty()
            } else {
                val errorText = response.body?.string().orEmpty()
                error("AI 请求失败(${response.code}): ${errorText.take(160)}")
            }

            val content = json.decodeFromString(ChatResponse.serializer(), responseText)
                .choices.firstOrNull()?.message?.content.orEmpty()
            require(content.isNotBlank()) { "AI 没有返回菜谱内容" }

            parseRecipe(content, recipeName)
        }
    }

    private fun parseRecipe(content: String, fallbackName: String): GeneratedRecipe {
        val cleanJson = content.trim().let { text ->
            val start = text.indexOf('{')
            val end = text.lastIndexOf('}')
            if (start >= 0 && end >= start) text.substring(start, end + 1) else text
        }

        val payload = json.decodeFromString(AiRecipePayload.serializer(), cleanJson)
        val category = if (payload.category in CATEGORIES) payload.category else "荤菜"
        val recipe = RecipeEntity(
            name = payload.name.ifBlank { fallbackName },
            category = category,
            description = payload.description,
            servings = payload.servings.coerceIn(1, 20),
            totalTime = payload.totalTime.coerceIn(1, 999)
        )
        val ingredients = payload.ingredients
            .filter { it.name.isNotBlank() }
            .map { IngredientEntity(recipeId = 0, name = it.name.trim(), quantity = it.quantity.trim(), unit = it.unit.trim()) }
        val steps = payload.steps
            .filter { it.description.isNotBlank() }
            .mapIndexed { index, step ->
                StepEntity(
                    recipeId = 0,
                    order = index + 1,
                    description = step.description.trim(),
                    timerSeconds = step.timerSeconds?.coerceIn(1, 24 * 60 * 60),
                    timerType = step.timerType?.takeIf { it in TIMER_TYPES }
                )
            }
        require(ingredients.isNotEmpty()) { "AI 生成结果缺少食材" }
        require(steps.isNotEmpty()) { "AI 生成结果缺少步骤" }

        return GeneratedRecipe(recipe = recipe, ingredients = ingredients, steps = steps)
    }

    private fun buildPrompt(recipeName: String): String = """
        请根据菜名「$recipeName」生成一个适合家庭烹饪的任务式菜谱。
        输出必须是严格 JSON，字段如下：
        {
          "name": "菜名",
          "category": "荤菜/素菜/汤类/主食/甜品/凉菜/小吃之一",
          "description": "一句简介",
          "servings": 2,
          "totalTime": 30,
          "ingredients": [{"name": "食材名", "quantity": "数量", "unit": "单位"}],
          "steps": [{"description": "步骤描述", "timerSeconds": 120, "timerType": "STIR_FRY/MARINATE/STEW/BLANCH/CUSTOM 或 null"}]
        }
        步骤要求：
        1. 每个步骤只做一件事，不要合并多个操作到一步
        2. 切菜、焯水、爆香、翻炒、调味、装盘都必须分开
        3. 需要固定时间的步骤(腌制X分钟、炒X秒、炖X分钟等)必须给出 timerSeconds
        4. 总步骤不应少于6步（简单菜谱也不少于4步）
    """.trimIndent()

    private companion object {
        val CATEGORIES = setOf("荤菜", "素菜", "汤类", "主食", "甜品", "凉菜", "小吃")
        val TIMER_TYPES = setOf("STIR_FRY", "MARINATE", "STEW", "BLANCH", "CUSTOM")
    }
}
