package com.cooking.plan.ui.recipe

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.text.KeyboardOptions
import com.cooking.plan.ui.theme.ChipShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.EditNote
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenHero
import com.cooking.plan.ui.components.KitchenSectionTitle
import com.cooking.plan.ui.components.KitchenTopBar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddRecipeScreen(
    recipeId: Long? = null,
    onBack: () -> Unit,
    viewModel: AddRecipeViewModel = hiltViewModel()
) {
    val isEdit = recipeId != null && recipeId > 0
    val name by viewModel.name.collectAsState()
    val category by viewModel.category.collectAsState()
    val description by viewModel.description.collectAsState()
    val servings by viewModel.servings.collectAsState()
    val totalTime by viewModel.totalTime.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val steps by viewModel.steps.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    var showDeleteDialog by remember { mutableStateOf(false) }
    var categoryExpanded by remember { mutableStateOf(false) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(recipeId) { if (isEdit) viewModel.loadRecipe(recipeId!!) }
    LaunchedEffect(Unit) { viewModel.saved.collect { onBack() } }
    LaunchedEffect(Unit) { viewModel.errorMessage.collect { snackbarHostState.showSnackbar(it) } }

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = if (isEdit) "编辑菜谱" else "新建菜谱",
                subtitle = if (isEdit) "调整口味、份量和步骤" else "把一道菜整理成可执行清单",
                navigation = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回")
                    }
                },
                action = {
                    if (isEdit) {
                        IconButton(onClick = { showDeleteDialog = true }) {
                            Icon(Icons.Default.Delete, "删除", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { padding ->
        KitchenBackground {
            LazyColumn(
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                item {
                    KitchenHero(
                        title = if (isEdit) "把这道菜打磨得更顺手" else "先写菜名，再补齐做法",
                        subtitle = if (isEdit) "分类、备菜、步骤和计时器都可以随时改。" else "也可以直接让 AI 生成一版初稿。"
                    )
                }

                item {
                    KitchenCard(Modifier.fillMaxWidth()) {
                        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedTextField(
                                value = name,
                                onValueChange = viewModel::updateName,
                                label = { Text(if (isEdit) "菜谱名称 *" else "输入菜名 *") },
                                leadingIcon = { Icon(Icons.Default.EditNote, null) },
                                modifier = Modifier.fillMaxWidth(),
                                singleLine = true
                            )
                            ExposedDropdownMenuBox(
                                expanded = categoryExpanded,
                                onExpandedChange = { categoryExpanded = it }
                            ) {
                                OutlinedTextField(
                                    value = category,
                                    onValueChange = {},
                                    readOnly = true,
                                    label = { Text("分类") },
                                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = categoryExpanded) },
                                    modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                )
                                ExposedDropdownMenu(expanded = categoryExpanded, onDismissRequest = { categoryExpanded = false }) {
                                    viewModel.categories.forEach { cat ->
                                        DropdownMenuItem(
                                            text = { Text(cat) },
                                            onClick = { viewModel.updateCategory(cat); categoryExpanded = false }
                                        )
                                    }
                                }
                            }
                            OutlinedTextField(
                                value = description,
                                onValueChange = viewModel::updateDescription,
                                label = { Text("简介") },
                                modifier = Modifier.fillMaxWidth(),
                                maxLines = 3
                            )
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedTextField(
                                    value = servings.toString(),
                                    onValueChange = { it.toIntOrNull()?.let(viewModel::updateServings) },
                                    label = { Text("份量") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    singleLine = true
                                )
                                OutlinedTextField(
                                    value = totalTime.toString(),
                                    onValueChange = { it.toIntOrNull()?.let(viewModel::updateTotalTime) },
                                    label = { Text("分钟") },
                                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                                    modifier = Modifier.weight(1f),
                                    singleLine = true
                                )
                            }
                        }
                    }
                }

                if (!isEdit) {
                    item {
                        FilledTonalButton(
                            onClick = viewModel::generateAndSave,
                            modifier = Modifier.fillMaxWidth(),
                            enabled = name.isNotBlank() && !isGenerating,
                            shape = ChipShape
                        ) {
                            Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text(if (isGenerating) "AI 生成中..." else "用 AI 生成并保存菜谱")
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        KitchenSectionTitle("食材", modifier = Modifier.weight(1f), subtitle = "名称、数量和单位会进入采购清单")
                        IconButton(onClick = viewModel::addIngredient) {
                            Icon(Icons.Default.Add, "添加食材")
                        }
                    }
                }

                itemsIndexed(ingredients) { index, ing ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        OutlinedTextField(
                            value = ing.name,
                            onValueChange = { viewModel.updateIngredient(index, ing.copy(name = it)) },
                            label = { Text("名称") },
                            modifier = Modifier.weight(2f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = ing.quantity,
                            onValueChange = { viewModel.updateIngredient(index, ing.copy(quantity = it)) },
                            label = { Text("量") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = ing.unit,
                            onValueChange = { viewModel.updateIngredient(index, ing.copy(unit = it)) },
                            label = { Text("单位") },
                            modifier = Modifier.weight(1f),
                            singleLine = true
                        )
                        IconButton(onClick = { viewModel.removeIngredient(index) }) {
                            Icon(Icons.Default.Close, "移除", tint = MaterialTheme.colorScheme.error)
                        }
                    }
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        KitchenSectionTitle("步骤", modifier = Modifier.weight(1f), subtitle = "需要提醒的环节可以添加计时器")
                        IconButton(onClick = viewModel::addStep) {
                            Icon(Icons.Default.Add, "添加步骤")
                        }
                    }
                }

                itemsIndexed(steps) { index, step ->
                    StepInputCard(
                        index = index,
                        step = step,
                        onUpdate = { viewModel.updateStep(index, it) },
                        onRemove = { viewModel.removeStep(index) }
                    )
                }

                item {
                    Spacer(modifier = Modifier.height(8.dp))
                    FilledTonalButton(
                        onClick = viewModel::save,
                        modifier = Modifier.fillMaxWidth(),
                        enabled = name.isNotBlank(),
                        shape = ChipShape
                    ) {
                        Text(if (isEdit) "保存修改" else "创建菜谱")
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title = { Text("删除菜谱") },
            text = { Text("确定要删除「$name」吗？此操作不可撤销。") },
            confirmButton = {
                TextButton(onClick = { viewModel.deleteRecipe(); showDeleteDialog = false }) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("取消") } }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun StepInputCard(
    index: Int,
    step: StepInput,
    onUpdate: (StepInput) -> Unit,
    onRemove: () -> Unit
) {
    var timerExpanded by remember { mutableStateOf(false) }
    val timerTypes = listOf("STIR_FRY" to "爆炒", "MARINATE" to "腌制", "STEW" to "炖煮", "BLANCH" to "焯水", "CUSTOM" to "自定义")
    var showTimerFields by remember { mutableStateOf(step.timerSeconds != null) }

    KitchenCard(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(14.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text("${index + 1}.", style = MaterialTheme.typography.titleSmall, modifier = Modifier.width(28.dp))
                OutlinedTextField(
                    value = step.description,
                    onValueChange = { onUpdate(step.copy(description = it)) },
                    label = { Text("步骤描述") },
                    modifier = Modifier.weight(1f),
                    maxLines = 3
                )
                IconButton(onClick = onRemove) {
                    Icon(Icons.Default.Close, "移除", tint = MaterialTheme.colorScheme.error)
                }
            }

        AnimatedVisibility(visible = showTimerFields) {
            Row(
                modifier = Modifier.fillMaxWidth().padding(start = 28.dp, top = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = step.timerSeconds?.toString() ?: "",
                    onValueChange = { onUpdate(step.copy(timerSeconds = it.toIntOrNull())) },
                    label = { Text("秒数") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                    singleLine = true
                )
                ExposedDropdownMenuBox(
                    expanded = timerExpanded,
                    onExpandedChange = { timerExpanded = it },
                    modifier = Modifier.weight(1.5f)
                ) {
                    OutlinedTextField(
                        value = timerTypes.find { it.first == step.timerType }?.second ?: "类型",
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(timerExpanded) },
                        modifier = Modifier.fillMaxWidth().menuAnchor(MenuAnchorType.PrimaryNotEditable),
                        singleLine = true
                    )
                    ExposedDropdownMenu(expanded = timerExpanded, onDismissRequest = { timerExpanded = false }) {
                        timerTypes.forEach { (key, label) ->
                            DropdownMenuItem(
                                text = { Text(label) },
                                onClick = { onUpdate(step.copy(timerType = key)); timerExpanded = false }
                            )
                        }
                    }
                }
                IconButton(onClick = { showTimerFields = false; onUpdate(step.copy(timerSeconds = null, timerType = null)) }) {
                    Icon(Icons.Default.Close, "移除计时", tint = MaterialTheme.colorScheme.error)
                }
            }
        }

        if (!showTimerFields) {
            TextButton(onClick = { showTimerFields = true }, modifier = Modifier.padding(start = 28.dp)) {
                Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                Spacer(modifier = Modifier.width(4.dp))
                Text("添加计时器")
            }
        }
        }
    }
}
