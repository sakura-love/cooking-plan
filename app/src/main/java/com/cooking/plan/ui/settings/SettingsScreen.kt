package com.cooking.plan.ui.settings

import android.app.Application
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.selection.SelectionContainer
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.ViewModel
import com.cooking.plan.data.settings.AiProvider
import com.cooking.plan.data.settings.AppSettingsRepository
import com.cooking.plan.data.settings.DarkModeSetting
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenSectionTitle
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.theme.InputShape
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val application: Application,
    private val settingsRepository: AppSettingsRepository
) : ViewModel() {
    val settings = settingsRepository.settings

    fun setDarkMode(mode: DarkModeSetting) = settingsRepository.setDarkMode(mode)
    fun setAiProvider(provider: AiProvider) = settingsRepository.setAiProvider(provider)
    fun setAiApiKey(apiKey: String) = settingsRepository.setAiApiKey(apiKey)
    fun setAiBaseUrl(baseUrl: String) = settingsRepository.setAiBaseUrl(baseUrl)
    fun setAiModel(model: String) = settingsRepository.setAiModel(model)

    fun getAppVersion(): String {
        return try {
            val pkg = application.packageManager.getPackageInfo(application.packageName, 0)
            val ver = pkg.versionName ?: "?"
            "v$ver · 让做饭像任务一样简单"
        } catch (_: Exception) {
            "料理计划"
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val settings by viewModel.settings.collectAsState()
    var providerExpanded by remember { mutableStateOf(false) }
    var darkModeExpanded by remember { mutableStateOf(false) }
    var aiAdvancedExpanded by remember { mutableStateOf(false) }
    var showApiGuide by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = "设置",
                subtitle = "外观、AI 和应用信息"
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        KitchenBackground {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    KitchenCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            KitchenSectionTitle("外观")
                            ExposedDropdownMenuBox(
                                expanded = darkModeExpanded,
                                onExpandedChange = { darkModeExpanded = it }
                            ) {
                                OutlinedTextField(
                                    value = settings.darkMode.label,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("深色模式") },
                                    leadingIcon = { Icon(Icons.Default.Palette, null) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(darkModeExpanded) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                )
                                ExposedDropdownMenu(expanded = darkModeExpanded, onDismissRequest = { darkModeExpanded = false }) {
                                    DarkModeSetting.entries.forEach { mode ->
                                        DropdownMenuItem(
                                            text = { Text(mode.label) },
                                            onClick = { viewModel.setDarkMode(mode); darkModeExpanded = false }
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    KitchenCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            KitchenSectionTitle("AI 菜谱生成", subtitle = "用于自动生成食材、步骤和计时器")
                            ExposedDropdownMenuBox(
                                expanded = providerExpanded,
                                onExpandedChange = { providerExpanded = it }
                            ) {
                                OutlinedTextField(
                                    value = settings.aiProvider.displayName,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("服务商") },
                                    leadingIcon = { Icon(Icons.Default.SmartToy, null) },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(providerExpanded) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                )
                                ExposedDropdownMenu(expanded = providerExpanded, onDismissRequest = { providerExpanded = false }) {
                                    AiProvider.entries.forEach { provider ->
                                        DropdownMenuItem(
                                            text = { Text(provider.displayName) },
                                            onClick = { viewModel.setAiProvider(provider); providerExpanded = false }
                                        )
                                    }
                                }
                            }
                            ExpandableSettingItem(
                                title = "API 设置",
                                subtitle = if (aiAdvancedExpanded) {
                                    "收起 API Key、地址和模型设置"
                                } else {
                                    "点击展开 API Key、地址和模型设置"
                                },
                                icon = Icons.Default.Key,
                                expanded = aiAdvancedExpanded,
                                onClick = { aiAdvancedExpanded = !aiAdvancedExpanded }
                            )
                            AnimatedVisibility(aiAdvancedExpanded) {
                                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                                    OutlinedTextField(
                                        value = settings.aiApiKey,
                                        onValueChange = viewModel::setAiApiKey,
                                        label = { Text("API Key") },
                                        leadingIcon = { Icon(Icons.Default.Key, null) },
                                        visualTransformation = PasswordVisualTransformation(),
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )
                                    OutlinedTextField(
                                        value = settings.aiBaseUrl,
                                        onValueChange = viewModel::setAiBaseUrl,
                                        label = { Text("API 地址") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )
                                    OutlinedTextField(
                                        value = settings.aiModel,
                                        onValueChange = viewModel::setAiModel,
                                        label = { Text("模型") },
                                        modifier = Modifier.fillMaxWidth(),
                                        singleLine = true
                                    )
                                }
                            }
                            SettingItem(
                                title = "API 获取教程",
                                subtitle = "查看 DeepSeek、智谱、OpenAI 和自定义平台的接入说明",
                                icon = Icons.Default.Info,
                                onClick = { showApiGuide = true }
                            )
                        }
                    }
                }

                item {
                    KitchenCard(Modifier.fillMaxWidth()) {
                        SettingItem(
                            title = "料理计划",
                            subtitle = viewModel.getAppVersion(),
                            icon = Icons.Default.Info,
                            onClick = {}
                        )
                    }
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }

    if (showApiGuide) {
        ApiGuideDialog(
            selectedProvider = settings.aiProvider,
            onDismiss = { showApiGuide = false }
        )
    }
}

private val DarkModeSetting.label: String
    get() = when (this) {
        DarkModeSetting.SYSTEM -> "跟随系统"
        DarkModeSetting.LIGHT -> "浅色"
        DarkModeSetting.DARK -> "深色"
}

@Composable
private fun ExpandableSettingItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    expanded: Boolean,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(42.dp),
            shape = InputShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(10.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(
            imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun SettingItem(
    title: String,
    subtitle: String,
    icon: ImageVector,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Surface(
            modifier = Modifier.size(42.dp),
            shape = InputShape,
            color = MaterialTheme.colorScheme.primaryContainer
        ) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(10.dp))
        }
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.bodyLarge)
            Text(subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private data class ApiGuide(
    val title: String,
    val website: String,
    val defaultBaseUrl: String,
    val defaultModel: String,
    val steps: List<String>,
    val note: String
)

@Composable
private fun ApiGuideDialog(
    selectedProvider: AiProvider,
    onDismiss: () -> Unit
) {
    val guides = remember {
        listOf(
            ApiGuide(
                title = "DeepSeek",
                website = "https://platform.deepseek.com/",
                defaultBaseUrl = AiProvider.DEEPSEEK.defaultBaseUrl,
                defaultModel = AiProvider.DEEPSEEK.defaultModel,
                steps = listOf(
                    "打开 DeepSeek 开放平台并注册/登录账号。",
                    "进入控制台里的 API Keys 页面，创建一个新的密钥。",
                    "复制生成的密钥，回到本应用填写到 API Key。",
                    "服务商选 DeepSeek，地址通常保持默认即可。"
                ),
                note = "如果默认模型无法使用，可在平台的模型列表里选择你账户已开通的模型名替换。"
            ),
            ApiGuide(
                title = "智谱",
                website = "https://open.bigmodel.cn/",
                defaultBaseUrl = AiProvider.ZHIPU.defaultBaseUrl,
                defaultModel = AiProvider.ZHIPU.defaultModel,
                steps = listOf(
                    "打开智谱开放平台并完成登录。",
                    "进入控制台或账户中心，找到 API Key 管理页面。",
                    "创建新密钥后复制到本应用的 API Key 输入框。",
                    "服务商选智谱，地址和模型先用默认值，再按你的开通情况调整。"
                ),
                note = "如果你使用的是智谱平台里其他模型版本，直接把模型名称改成控制台显示的实际可用值。"
            ),
            ApiGuide(
                title = "OpenAI",
                website = "https://platform.openai.com/",
                defaultBaseUrl = AiProvider.OPENAI.defaultBaseUrl,
                defaultModel = AiProvider.OPENAI.defaultModel,
                steps = listOf(
                    "打开 OpenAI Platform 并登录账号。",
                    "进入 Settings 或 Dashboard 中的 API keys 页面创建密钥。",
                    "复制密钥后粘贴到本应用的 API Key 输入框。",
                    "服务商选 OpenAI，地址通常保持默认，模型可继续用预填值。"
                ),
                note = "如果你的账号没有该模型权限，去平台控制台查看可用模型后替换即可。"
            ),
            ApiGuide(
                title = "自定义兼容平台",
                website = "例如：硅基流动、OpenRouter、阿里百炼、火山方舟等",
                defaultBaseUrl = "填写平台提供的 OpenAI 兼容 Base URL",
                defaultModel = "填写平台要求的模型名",
                steps = listOf(
                    "确认目标平台提供 OpenAI 兼容接口。",
                    "在该平台后台创建 API Key。",
                    "把平台文档给出的 Base URL、模型名和密钥分别填入本应用。",
                    "服务商选择自定义，避免被默认地址覆盖。"
                ),
                note = "兼容平台最常见的问题是地址末尾层级不对，尽量直接照抄平台文档里的 Base URL。"
            )
        )
    }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("知道了")
            }
        },
        title = { Text("API 获取教程") },
        text = {
            SelectionContainer {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .heightIn(max = 520.dp)
                        .verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(
                        text = "当前已选服务商：${selectedProvider.displayName}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "普通用户可以先直接使用内置菜谱；需要 AI 自动生成时，再按下面步骤申请并填写 API。",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    guides.forEach { guide ->
                        Surface(
                            shape = InputShape,
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.4f)
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(12.dp),
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Text(guide.title, style = MaterialTheme.typography.titleMedium)
                                Text(
                                    text = "站点：${guide.website}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "默认地址：${guide.defaultBaseUrl}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = "默认模型：${guide.defaultModel}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                guide.steps.forEachIndexed { index, step ->
                                    Text(
                                        text = "${index + 1}. $step",
                                        style = MaterialTheme.typography.bodySmall
                                    )
                                }
                                Text(
                                    text = "提示：${guide.note}",
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }
            }
        }
    )
}
