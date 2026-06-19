package com.cooking.plan.ui.plan

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.EventNote
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.data.local.WeeklyPlanEntity
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenChip
import com.cooking.plan.ui.components.KitchenEmptyState
import com.cooking.plan.ui.components.KitchenHero
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.components.RecipeBadge
import com.cooking.plan.ui.components.recipeAccent
import com.cooking.plan.ui.theme.CardShape
import com.cooking.plan.ui.theme.InputShape

private val dayNames = listOf("周一", "周二", "周三", "周四", "周五", "周六", "周日")
private val mealMeta = listOf(
    "BREAKFAST" to MealInfo("早餐", "早", Color(0xFFC08A20)),
    "LUNCH" to MealInfo("午餐", "午", Color(0xFFD94B34)),
    "DINNER" to MealInfo("晚餐", "晚", Color(0xFF2E7D5B))
)
private data class MealInfo(val label: String, val mark: String, val color: Color)

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlanScreen(
    onNavigateToMeal: (year: Int, weekNumber: Int, dayOfWeek: Int, meal: String) -> Unit,
    viewModel: PlanViewModel = hiltViewModel()
) {
    val plans by viewModel.plans.collectAsState()
    val allRecipes by viewModel.allRecipes.collectAsState()
    val pickerSlot by viewModel.showRecipePicker.collectAsState()
    val currentDay = viewModel.getCurrentDayOfWeek()

    val plansByDay = remember(plans) { derivedStateOf { plans.groupBy { it.dayOfWeek } } }
    val recipeById = remember(allRecipes) { derivedStateOf { allRecipes.associateBy { it.id } } }

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = "每周计划",
                subtitle = "${plans.size} 道菜已安排"
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
                    KitchenHero(
                        title = "一周三餐排好，下厨更轻松",
                        subtitle = "早餐、午餐、晚餐都能快速绑定菜谱。"
                    )
                }

                items(7) { dayIndex ->
                    val dayOfWeek = dayIndex + 1
                    val isToday = dayOfWeek == currentDay
                    val dayPlans = plansByDay.value[dayOfWeek].orEmpty()

                    DayCard(
                        dayName = dayNames[dayIndex],
                        isToday = isToday,
                        dayPlans = dayPlans,
                        recipeById = recipeById.value,
                        onOpenMeal = { meal -> onNavigateToMeal(viewModel.year, viewModel.weekNumber, dayOfWeek, meal) },
                        onOpenShopping = { onNavigateToMeal(viewModel.year, viewModel.weekNumber, dayOfWeek, "SHOPPING") },
                        onAddMeal = { meal -> viewModel.openRecipePicker(dayOfWeek, meal) },
                        onRemovePlan = { plan -> viewModel.removePlan(plan.dayOfWeek, plan.meal, plan.recipeId) }
                    )
                }

                item { Spacer(modifier = Modifier.height(16.dp)) }
            }
        }
    }

    pickerSlot?.let { slot ->
        val mealInfo = mealMeta.find { it.first == slot.meal }?.second
        val selectedRecipeIds = plans
            .filter { it.dayOfWeek == slot.dayOfWeek && it.meal == slot.meal }
            .map { it.recipeId }
            .toSet()
        RecipePickerSheet(
            recipes = allRecipes,
            mealLabel = mealInfo?.label ?: slot.meal,
            selectedRecipeIds = selectedRecipeIds,
            onSelect = { recipe -> viewModel.assignRecipe(slot.dayOfWeek, slot.meal, recipe.id) },
            onDismiss = viewModel::closeRecipePicker
        )
    }
}

@Composable
private fun DayCard(
    dayName: String,
    isToday: Boolean,
    dayPlans: List<WeeklyPlanEntity>,
    recipeById: Map<Long, RecipeEntity>,
    onOpenMeal: (String) -> Unit,
    onOpenShopping: () -> Unit,
    onAddMeal: (String) -> Unit,
    onRemovePlan: (WeeklyPlanEntity) -> Unit
) {
    KitchenCard(
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Surface(
                    shape = CardShape,
                    color = if (isToday) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surfaceVariant,
                    contentColor = if (isToday) MaterialTheme.colorScheme.onPrimary else MaterialTheme.colorScheme.onSurfaceVariant
                ) {
                    Text(
                        dayName,
                        style = MaterialTheme.typography.titleSmall,
                        modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(8.dp))
                if (isToday) {
                    KitchenChip("今天下厨", color = MaterialTheme.colorScheme.primary)
                }
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    "${dayPlans.map { it.meal }.distinct().size}/3",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (dayPlans.isNotEmpty()) {
                Spacer(modifier = Modifier.height(10.dp))
                TextButton(onClick = onOpenShopping) {
                    Icon(Icons.Default.ShoppingCart, null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("买菜清单")
                }
            }
            Spacer(modifier = Modifier.height(12.dp))

            mealMeta.forEach { (mealKey, mealInfo) ->
                val mealPlans = dayPlans.filter { it.meal == mealKey }
                val recipes = mealPlans.mapNotNull { p -> recipeById[p.recipeId] }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable(enabled = recipes.isNotEmpty()) { onOpenMeal(mealKey) }
                        .padding(vertical = 5.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Surface(
                        modifier = Modifier.size(34.dp),
                        shape = CardShape,
                        color = mealInfo.color.copy(alpha = 0.12f),
                        border = BorderStroke(1.dp, mealInfo.color.copy(alpha = 0.22f))
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(mealInfo.mark, style = MaterialTheme.typography.labelLarge, color = mealInfo.color)
                        }
                    }
                    Spacer(modifier = Modifier.width(10.dp))
                    Column(modifier = Modifier.weight(1f)) {
                        Text(mealInfo.label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        if (recipes.isNotEmpty()) {
                            Text(
                                recipes.joinToString("、") { it.name },
                                style = MaterialTheme.typography.bodyLarge,
                                maxLines = 2,
                                overflow = TextOverflow.Ellipsis
                            )
                            Text(
                                "${recipes.size} 道菜",
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }

                    TextButton(onClick = { onAddMeal(mealKey) }) {
                        Icon(Icons.Default.Add, null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(3.dp))
                        Text(if (recipes.isEmpty()) "安排" else "加菜")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipePickerSheet(
    recipes: List<RecipeEntity>,
    mealLabel: String,
    selectedRecipeIds: Set<Long>,
    onSelect: (RecipeEntity) -> Unit,
    onDismiss: () -> Unit
) {
    val sheetState = rememberModalBottomSheetState()
    var query by remember { mutableStateOf("") }
    val filteredRecipes = remember(recipes, mealLabel, query) {
        recipes
            .asSequence()
            .filter { recipe ->
                query.isBlank() ||
                    recipe.name.contains(query, ignoreCase = true) ||
                    recipe.category.contains(query, ignoreCase = true) ||
                    recipe.description.contains(query, ignoreCase = true)
            }
            .sortedWith(
                compareByDescending<RecipeEntity> { it.isGoodForMeal(mealLabel) }
                    .thenBy { it.category }
                    .thenBy { it.totalTime }
                    .thenBy { it.name }
            )
            .toList()
    }

    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState
    ) {
        Column(modifier = Modifier.padding(16.dp)) {
            KitchenTopBar(title = "选择$mealLabel", subtitle = "搜索菜名或分类，手动安排到计划")
            Spacer(modifier = Modifier.height(8.dp))

            if (recipes.isEmpty()) {
                KitchenEmptyState(
                    icon = Icons.AutoMirrored.Filled.EventNote,
                    title = "还没有可安排的菜谱",
                    subtitle = "先去首页创建菜谱，再回来排计划。"
                )
            } else {
                TextField(
                    value = query,
                    onValueChange = { query = it },
                    placeholder = { Text("输入菜名、分类或关键词") },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary) },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.colors(
                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                        focusedIndicatorColor = Color.Transparent,
                        unfocusedIndicatorColor = Color.Transparent
                    ),
                    shape = InputShape,
                    singleLine = true
                )
                Spacer(modifier = Modifier.height(10.dp))

                if (filteredRecipes.isEmpty()) {
                    KitchenEmptyState(
                        icon = Icons.Default.Search,
                        title = "没有找到这道菜",
                        subtitle = "换个菜名或分类试试。"
                    )
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxWidth().height(420.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(filteredRecipes, key = { it.id }) { recipe ->
                            val isSelected = recipe.id in selectedRecipeIds
                            KitchenCard(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clickable { onSelect(recipe) }
                            ) {
                                Row(modifier = Modifier.padding(14.dp), verticalAlignment = Alignment.CenterVertically) {
                                    Surface(
                                        modifier = Modifier.size(38.dp),
                                        shape = CardShape,
                                        color = recipeAccent(recipe.category).copy(alpha = 0.14f)
                                    ) {
                                        Box(contentAlignment = Alignment.Center) {
                                            Icon(Icons.Default.WbSunny, null, tint = recipeAccent(recipe.category), modifier = Modifier.size(20.dp))
                                        }
                                    }
                                    Spacer(Modifier.width(10.dp))
                                    Column(Modifier.weight(1f)) {
                                        Text(recipe.name, style = MaterialTheme.typography.bodyLarge, fontWeight = FontWeight.Medium)
                                        Text(
                                            "${recipe.servings} 人份 · ${recipe.totalTime} 分钟",
                                            style = MaterialTheme.typography.bodySmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                    if (isSelected) {
                                        KitchenChip("已选", color = MaterialTheme.colorScheme.primary)
                                    } else {
                                        RecipeBadge(recipe.category)
                                    }
                                }
                            }
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}

private fun RecipeEntity.isGoodForMeal(mealLabel: String): Boolean {
    val text = "$name $category $description"
    return when (mealLabel) {
        "早餐" -> text.contains("粥") || text.contains("面") || text.contains("蛋") || text.contains("饭")
        "午餐", "晚餐" -> category != "甜品"
        else -> true
    }
}
