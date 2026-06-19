package com.cooking.plan.ui.plan

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenEmptyState
import com.cooking.plan.ui.components.KitchenTopBar

data class DailyShoppingIngredient(
    val key: String,
    val name: String,
    val quantityText: String,
    val recipeNames: String,
    val checked: Boolean
)

@Composable
fun DailyShoppingListScreen(
    year: Int,
    weekNumber: Int,
    dayOfWeek: Int,
    onBack: () -> Unit,
    viewModel: DailyShoppingListViewModel = hiltViewModel()
) {
    val plans by viewModel.getPlans(year, weekNumber).collectAsState(initial = emptyList())
    val recipes by viewModel.allRecipes.collectAsState()
    val checks by viewModel.getChecks(year, weekNumber, dayOfWeek).collectAsState(initial = emptyList())
    val ingredientsByRecipe by viewModel.ingredientsByRecipe.collectAsState()

    val recipeById = remember(recipes) { derivedStateOf { recipes.associateBy { it.id } } }
    val dayPlans = remember(plans, dayOfWeek) { plans.filter { it.dayOfWeek == dayOfWeek } }
    val checkMap = remember(checks) { checks.associateBy { it.itemKey } }
    val items = remember(dayPlans, recipeById.value, ingredientsByRecipe, checkMap) {
        dayPlans
            .flatMap { plan ->
                val recipe = recipeById.value[plan.recipeId]
                ingredientsByRecipe[plan.recipeId].orEmpty().mapNotNull { ingredient ->
                    val normalized = ingredient.toShoppingIngredient() ?: return@mapNotNull null
                    normalized.key to Triple(normalized, recipe?.name.orEmpty(), checkMap[normalized.key]?.checked == true)
                }
            }
            .groupBy({ it.first }, { it.second })
            .map { (key, values) ->
                val first = values.first().first
                DailyShoppingIngredient(
                    key = key,
                    name = first.name,
                    quantityText = mergeQuantityText(values.map { it.first }),
                    recipeNames = values.map { it.second }.filter { it.isNotBlank() }.distinct().joinToString("、"),
                    checked = values.any { it.third }
                )
            }
            .sortedWith(compareBy<DailyShoppingIngredient> { it.checked }.thenBy { it.name })
    }

    androidx.compose.runtime.LaunchedEffect(dayPlans) {
        viewModel.loadIngredients(dayPlans.map { it.recipeId }.distinct())
    }

    val checkedCount = items.count { it.checked }

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = "${dayLabel(dayOfWeek)}买菜清单",
                subtitle = "${items.size - checkedCount} 项待买 · $checkedCount 项已买",
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
            if (items.isEmpty()) {
                Box(Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    KitchenEmptyState(
                        icon = Icons.Default.ShoppingCart,
                        title = "当天还没有买菜项",
                        subtitle = "先在每周计划里给这一天安排菜谱。"
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(items, key = { it.key }) { item ->
                        DailyShoppingItemCard(
                            item = item,
                            onToggle = {
                                viewModel.setChecked(year, weekNumber, dayOfWeek, item.key, !item.checked)
                            }
                        )
                    }
                    item { Spacer(Modifier.height(16.dp)) }
                }
            }
        }
    }
}

@Composable
private fun DailyShoppingItemCard(
    item: DailyShoppingIngredient,
    onToggle: () -> Unit
) {
    KitchenCard(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth().clickable { onToggle() }.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(checked = item.checked, onCheckedChange = { onToggle() })
            Spacer(Modifier.width(8.dp))
            Column(Modifier.weight(1f)) {
                Text(
                    item.name,
                    style = MaterialTheme.typography.bodyLarge,
                    textDecoration = if (item.checked) TextDecoration.LineThrough else null,
                    color = if (item.checked) MaterialTheme.colorScheme.onSurfaceVariant else MaterialTheme.colorScheme.onSurface
                )
                Text(
                    item.quantityText,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                if (item.recipeNames.isNotBlank()) {
                    Text(
                        item.recipeNames,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private data class ShoppingIngredientSource(
    val key: String,
    val name: String,
    val quantity: String,
    val unit: String
)

private fun com.cooking.plan.data.local.IngredientEntity.toShoppingIngredient(): ShoppingIngredientSource? {
    val rawName = name.trim()
    if (rawName.isBlank()) return null

    val compactName = rawName
        .replace("适量", "")
        .replace("少许", "")
        .trim()

    if (compactName in commonPantryItems) return null
    if (commonPantryKeywords.any { compactName.contains(it) }) return null

    val shoppingName = when {
        compactName in scallionForms -> "小葱"
        compactName in gingerForms -> "姜"
        compactName in garlicForms -> "大蒜"
        else -> compactName
    }

    return ShoppingIngredientSource(
        key = shoppingName,
        name = shoppingName,
        quantity = quantity.trim(),
        unit = unit.trim()
    )
}

private val commonPantryItems = setOf(
    "盐", "食盐", "糖", "白糖", "冰糖",
    "生抽", "老抽", "酱油", "料酒",
    "醋", "白醋", "陈醋", "香醋",
    "油", "食用油", "植物油", "花生油", "玉米油", "菜籽油", "香油",
    "淀粉", "白胡椒", "白胡椒粉", "胡椒粉", "花椒粉"
)

private val commonPantryKeywords = setOf(
    "生抽", "老抽", "酱油", "料酒", "食用油", "香油", "淀粉", "胡椒粉"
)

private val scallionForms = setOf("葱", "小葱", "葱花", "葱段", "葱丝", "葱末")
private val gingerForms = setOf("姜", "生姜", "姜片", "姜丝", "姜末")
private val garlicForms = setOf("蒜", "大蒜", "蒜瓣", "蒜末", "蒜片", "蒜蓉")

private fun mergeQuantityText(ingredients: List<ShoppingIngredientSource>): String {
    val parts = ingredients
        .map { "${it.quantity}${it.unit}".trim() }
        .filter { it.isNotBlank() }
    return if (parts.isEmpty()) {
        "按需"
    } else {
        parts.groupingBy { it }.eachCount()
            .map { (text, count) -> if (count > 1) "$text × $count" else text }
            .joinToString(" + ")
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
