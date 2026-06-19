package com.cooking.plan.ui.recipe

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material.icons.filled.Stop
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooking.plan.data.local.StepEntity
import com.cooking.plan.data.timer.TimerInfo
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenChip
import com.cooking.plan.ui.components.KitchenHero
import com.cooking.plan.ui.components.KitchenSectionTitle
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.components.RecipeBadge
import com.cooking.plan.ui.components.recipeAccent
import com.cooking.plan.ui.theme.CardShape
import com.cooking.plan.ui.theme.InputShape
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    recipeId: Long,
    onBack: () -> Unit,
    viewModel: RecipeDetailViewModel = hiltViewModel()
) {
    val recipe by viewModel.recipe.collectAsState()
    val ingredients by viewModel.ingredients.collectAsState()
    val steps by viewModel.steps.collectAsState()
    val currentStepIndex by viewModel.currentStepIndex.collectAsState()
    val completedSteps by viewModel.completedSteps.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()
    val isCooking = recipeId > 0 && settings.activeCookingRecipeId == recipeId

    LaunchedEffect(recipeId) { viewModel.loadRecipe(recipeId) }

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = recipe?.name ?: "菜谱详情",
                subtitle = recipe?.category ?: "准备下厨",
                navigation = {
                    IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Filled.ArrowBack, "返回") }
                },
                action = {
                    IconButton(onClick = { viewModel.toggleFavorite() }) {
                        Icon(
                            if (recipe?.isFavorite == true) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                            "收藏",
                            tint = if (recipe?.isFavorite == true) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        KitchenBackground {
            LazyColumn(
                state = listState,
                modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    recipe?.let { r ->
                        KitchenHero(
                            title = r.name,
                            subtitle = r.description.ifBlank { "按步骤完成这道菜，计时器会在关键步骤提醒。" },
                            trailing = {
                                Surface(
                                    modifier = Modifier.size(56.dp),
                                    shape = CardShape,
                                    color = recipeAccent(r.category).copy(alpha = 0.14f),
                                    border = BorderStroke(1.dp, recipeAccent(r.category).copy(alpha = 0.22f))
                                ) {
                                    Box(contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Restaurant, null, tint = recipeAccent(r.category), modifier = Modifier.size(30.dp))
                                    }
                                }
                            }
                        )
                        Spacer(Modifier.height(10.dp))
                        Row {
                            RecipeBadge(r.category)
                            Spacer(Modifier.width(8.dp))
                            KitchenChip("${r.servings} 人份", color = MaterialTheme.colorScheme.secondary)
                            Spacer(Modifier.width(8.dp))
                            KitchenChip("${r.totalTime} 分钟", color = MaterialTheme.colorScheme.tertiary)
                        }
                        Spacer(Modifier.height(10.dp))
                        FilledTonalButton(
                            onClick = {
                                if (isCooking) viewModel.finishCooking() else viewModel.markCooking()
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Icon(
                                if (isCooking) Icons.Default.Stop else Icons.Default.PlayArrow,
                                null,
                                modifier = Modifier.size(18.dp)
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(if (isCooking) "结束烹饪" else "开始烹饪")
                        }
                    }
                }

                if (ingredients.isNotEmpty()) {
                    item {
                        KitchenSectionTitle("备菜清单", subtitle = "下锅前把食材一次核齐")
                    }
                    item {
                        KitchenCard(Modifier.fillMaxWidth()) {
                            Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                                ingredients.forEach { ing ->
                                    Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                                        Text(ing.name, style = MaterialTheme.typography.bodyMedium)
                                        Text(
                                            "${ing.quantity}${ing.unit}",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }

                item {
                    val stepProgress by remember(steps, completedSteps) {
                        derivedStateOf { if (steps.isEmpty()) 0f else completedSteps.size.toFloat() / steps.size }
                    }
                    KitchenSectionTitle("烹饪步骤", subtitle = "照着顺序做，完成一步勾一步")
                    if (steps.isNotEmpty()) {
                        Row(Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                            LinearProgressIndicator(
                                progress = { stepProgress },
                                modifier = Modifier.weight(1f).height(8.dp).clip(CardShape),
                                trackColor = MaterialTheme.colorScheme.surfaceVariant
                            )
                            Spacer(Modifier.width(10.dp))
                            Text(
                                "${completedSteps.size}/${steps.size}",
                                style = MaterialTheme.typography.labelLarge,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                itemsIndexed(
                    items = steps,
                    key = { _, step -> step.id },
                    contentType = { _, _ -> "step" }
                ) { index, step ->
                    val isCompleted = index in completedSteps
                    val isCurrent = index == currentStepIndex
                    val timerState = viewModel.activeTimers[index]

                    StepCard(
                        index = index,
                        step = step,
                        isCompleted = isCompleted,
                        isCurrent = isCurrent,
                        timerState = timerState,
                        onComplete = {
                            viewModel.completeStep(index)
                            scope.launch { if (index + 1 < steps.size) listState.animateScrollToItem(index + 1) }
                        },
                        onStartTimer = { viewModel.startTimer(index, step.timerSeconds!!, step.description) },
                        onStopTimer = { viewModel.stopTimer(index) },
                        onGoToStep = { viewModel.goToStep(index) }
                    )
                }

                if (currentStepIndex >= steps.size && steps.isNotEmpty()) {
                    item {
                        KitchenCard(
                            modifier = Modifier.fillMaxWidth(),
                            emphasized = true
                        ) {
                            Column(
                                Modifier.padding(24.dp).fillMaxWidth(),
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Text("出锅完成", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.secondary)
                                Spacer(Modifier.height(4.dp))
                                Text("好菜上桌，趁热享用。", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                    }
                }

                item { Spacer(Modifier.height(32.dp)) }
            }
        }
    }
}

@Composable
private fun StepCard(
    index: Int,
    step: StepEntity,
    isCompleted: Boolean,
    isCurrent: Boolean,
    timerState: TimerInfo?,
    onComplete: () -> Unit,
    onStartTimer: () -> Unit,
    onStopTimer: () -> Unit,
    onGoToStep: () -> Unit
) {
    KitchenCard(
        modifier = Modifier.fillMaxWidth(),
        emphasized = false
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    modifier = Modifier.size(38.dp),
                    shape = CardShape,
                    color = when {
                        isCompleted -> MaterialTheme.colorScheme.secondary
                        isCurrent -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.surfaceVariant
                    },
                    contentColor = when {
                        isCompleted -> MaterialTheme.colorScheme.onSecondary
                        isCurrent -> MaterialTheme.colorScheme.onPrimary
                        else -> MaterialTheme.colorScheme.onSurfaceVariant
                    }
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text("${index + 1}", style = MaterialTheme.typography.labelLarge)
                    }
                }
                Spacer(Modifier.width(12.dp))
                Column(Modifier.weight(1f)) {
                    Text(
                        step.description,
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (isCompleted) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                    )
                    if (step.timerSeconds != null && timerState == null) {
                        val min = step.timerSeconds / 60
                        val sec = step.timerSeconds % 60
                        Spacer(Modifier.height(3.dp))
                        Text(
                            "${timerLabel(step.timerType)} · ${if (min > 0) "${min}分" else ""}${if (sec > 0) "${sec}秒" else ""}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.tertiary
                        )
                    }
                }

                when {
                    isCompleted -> Icon(Icons.Default.Check, "已完成", tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.size(24.dp))
                    isCurrent && !isCompleted -> IconButton(onClick = onComplete) { Icon(Icons.Default.Check, "完成", tint = MaterialTheme.colorScheme.primary) }
                }
            }

            if (timerState != null) {
                Spacer(Modifier.height(12.dp))
                Surface(
                    modifier = Modifier.fillMaxWidth(),
                    shape = CardShape,
                    color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f),
                    border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f))
                ) {
                    Row(
                        Modifier.padding(12.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(
                                formatTimerDisplay(timerState.remainingSeconds),
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (timerState.remainingSeconds <= 10 && timerState.isRunning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary
                            )
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(top = 6.dp)
                                    .height(8.dp)
                                    .clip(InputShape)
                                    .background(MaterialTheme.colorScheme.surface)
                            ) {
                                LinearProgressIndicator(
                                    progress = { timerState.progress },
                                    modifier = Modifier.fillMaxSize(),
                                    color = if (timerState.remainingSeconds <= 10 && timerState.isRunning) MaterialTheme.colorScheme.error else MaterialTheme.colorScheme.primary,
                                    trackColor = Color.Transparent
                                )
                            }
                        }
                        Spacer(Modifier.width(12.dp))
                        if (timerState.isRunning) {
                            IconButton(onClick = onStopTimer) { Icon(Icons.Default.Stop, "停止", tint = MaterialTheme.colorScheme.error) }
                        } else if (timerState.remainingSeconds == 0) {
                            Text("完成", color = MaterialTheme.colorScheme.secondary, fontWeight = FontWeight.Bold)
                        }
                    }
                }
            }

            if (isCurrent && !isCompleted && step.timerSeconds != null && timerState == null) {
                Spacer(Modifier.height(10.dp))
                FilledTonalButton(onClick = onStartTimer, modifier = Modifier.fillMaxWidth()) {
                    Icon(Icons.Default.PlayArrow, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(6.dp))
                    Text("开始计时")
                }
            }
        }
    }
}

private fun timerLabel(type: String?): String = when (type) {
    "STIR_FRY" -> "爆炒"
    "MARINATE" -> "腌制"
    "STEW" -> "炖煮"
    "BLANCH" -> "焯水"
    else -> "计时"
}

private fun formatTimerDisplay(seconds: Int): String {
    val min = seconds / 60
    val sec = seconds % 60
    return String.format("%02d:%02d", min, sec)
}
