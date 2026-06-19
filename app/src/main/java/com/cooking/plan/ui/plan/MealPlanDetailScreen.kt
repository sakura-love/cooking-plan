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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import com.cooking.plan.ui.components.KitchenEmptyState
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.components.RecipeBadge
import com.cooking.plan.ui.components.recipeAccent
import com.cooking.plan.ui.theme.CardShape

@Composable
fun MealPlanDetailScreen(
    year: Int,
    weekNumber: Int,
    dayOfWeek: Int,
    meal: String,
    onBack: () -> Unit,
    onNavigateToRecipe: (Long) -> Unit,
    viewModel: MealPlanDetailViewModel = hiltViewModel()
) {
    val plans by viewModel.getPlans(year, weekNumber).collectAsState(initial = emptyList())
    val recipes by viewModel.allRecipes.collectAsState()
    val recipeById = remember(recipes) { derivedStateOf { recipes.associateBy { it.id } } }
    val mealPlans = remember(plans, dayOfWeek, meal) {
        plans.filter { it.dayOfWeek == dayOfWeek && it.meal == meal }
    }
    val mealRecipes = mealPlans.mapNotNull { plan -> recipeById.value[plan.recipeId]?.let { plan to it } }
    val title = "${dayLabel(dayOfWeek)}${mealLabel(meal)}"

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = title,
                subtitle = "${mealRecipes.size} 道菜",
                navigation = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "返回")
                    }
                }
            )
        },
        containerColor = Color.Transparent
    ) { padding ->
        KitchenBackground {
            if (mealRecipes.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize().padding(padding),
                    contentAlignment = Alignment.Center
                ) {
                    KitchenEmptyState(
                        icon = Icons.Default.Restaurant,
                        title = "这顿饭还没有菜",
                        subtitle = "回到每周计划里添加菜谱。"
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(mealRecipes, key = { it.first.id }) { (plan, recipe) ->
                        MealRecipeCard(
                            recipe = recipe,
                            onOpen = { onNavigateToRecipe(recipe.id) },
                            onRemove = { viewModel.removePlan(plan) }
                        )
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
private fun MealRecipeCard(
    recipe: RecipeEntity,
    onOpen: () -> Unit,
    onRemove: () -> Unit
) {
    val accent = recipeAccent(recipe.category)
    KitchenCard(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onOpen)
    ) {
        Row(modifier = Modifier.padding(16.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(
                modifier = Modifier.size(46.dp),
                shape = CardShape,
                color = accent.copy(alpha = 0.13f),
                border = BorderStroke(1.dp, accent.copy(alpha = 0.22f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Text(
                        recipe.category.take(1).ifBlank { "菜" },
                        style = MaterialTheme.typography.titleSmall,
                        color = accent,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Spacer(Modifier.width(12.dp))
            Column(Modifier.weight(1f)) {
                RecipeBadge(recipe.category)
                Spacer(Modifier.height(6.dp))
                Text(
                    recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "${recipe.servings} 人份 · ${recipe.totalTime} 分钟",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onRemove, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.Close, contentDescription = "移除", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

private fun dayLabel(dayOfWeek: Int): String = when (dayOfWeek) {
    1 -> "周一"
    2 -> "周二"
    3 -> "周三"
    4 -> "周四"
    5 -> "周五"
    6 -> "周六"
    7 -> "周日"
    else -> ""
}

private fun mealLabel(meal: String): String = when (meal) {
    "BREAKFAST" -> "早餐"
    "LUNCH" -> "午餐"
    "DINNER" -> "晚餐"
    else -> meal
}
