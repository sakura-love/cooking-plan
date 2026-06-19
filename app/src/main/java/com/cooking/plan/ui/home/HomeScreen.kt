package com.cooking.plan.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Kitchen
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timer
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenChip
import com.cooking.plan.ui.components.KitchenEmptyState
import com.cooking.plan.ui.components.KitchenSectionTitle
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.components.RecipeBadge
import com.cooking.plan.ui.components.RecipeGradientCard
import com.cooking.plan.ui.theme.InputShape

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToRecipe: (Long) -> Unit,
    onNavigateToAddRecipe: (Long?) -> Unit,
    onNavigateToBeginnerGuide: () -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val recipes by viewModel.recipes.collectAsState(initial = emptyList())
    val groupedRecipes by viewModel.groupedRecipes.collectAsState(initial = emptyList())
    val categoryTabs by viewModel.categoryTabs.collectAsState(initial = emptyList())
    val activeCookingRecipe by viewModel.activeCookingRecipe.collectAsState(initial = null)
    val searchQuery by viewModel.searchQuery.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isGenerating by viewModel.isGenerating.collectAsState()
    var showDeleteDialog by remember { mutableStateOf<RecipeEntity?>(null) }
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is HomeEvent.OpenRecipe -> onNavigateToRecipe(event.recipeId)
                is HomeEvent.ShowMessage -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = "料理计划",
                subtitle = "${recipes.size} 道菜谱 · 今天想吃什么",
                action = {
                    TextButton(onClick = onNavigateToBeginnerGuide) {
                        Text("新手必看")
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onNavigateToAddRecipe(null) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 4.dp)
            ) { Icon(Icons.Default.Add, "新建菜谱") }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
        containerColor = Color.Transparent
    ) { padding ->
        KitchenBackground {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                TextField(
                    value = searchQuery,
                    onValueChange = viewModel::updateSearch,
                    placeholder = { Text("搜索菜名、分类、简介或食材") },
                    leadingIcon = { Icon(Icons.Default.Search, null, tint = MaterialTheme.colorScheme.primary) },
                    trailingIcon = {
                        if (searchQuery.isNotBlank()) {
                            IconButton(onClick = { viewModel.updateSearch("") }) {
                                Icon(
                                    Icons.Default.Close,
                                    contentDescription = "清空搜索",
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    },
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

                Spacer(Modifier.height(16.dp))

                if (recipes.isEmpty()) {
                    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        KitchenEmptyState(
                            icon = Icons.Default.Kitchen,
                            title = if (searchQuery.isBlank()) "厨房还没有菜单" else "没有找到这道菜",
                            subtitle = if (searchQuery.isBlank()) {
                                "可以先手动整理常做菜，后面排计划和采购会顺手很多。"
                            } else {
                                "换个食材名、分类或做法关键词试试，也可以直接手动新建。"
                            },
                            action = {
                                if (searchQuery.isNotBlank()) {
                                    FilledTonalButton(
                                        onClick = viewModel::generateRecipeFromSearch,
                                        enabled = !isGenerating
                                    ) {
                                        Icon(Icons.Default.AutoAwesome, null, modifier = Modifier.size(18.dp))
                                        Spacer(Modifier.width(6.dp))
                                        Text(if (isGenerating) "生成中..." else "AI 补一版「$searchQuery」")
                                    }
                                }
                            }
                        )
                    }
                } else {
                    LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (searchQuery.isBlank()) {
                            activeCookingRecipe?.let { recipe ->
                                item(key = "active_cooking") {
                                    ActiveCookingCard(
                                        recipe = recipe,
                                        onClick = { onNavigateToRecipe(recipe.id) },
                                        onClear = { viewModel.clearActiveCooking(recipe.id) }
                                    )
                                }
                            }
                        }
                        item {
                            HomeCategoryDirectory(
                                tabs = categoryTabs,
                                onSelect = { label ->
                                    viewModel.selectCategory(label.takeUnless { it == "全部" })
                                }
                            )
                        }
                        item {
                            KitchenSectionTitle(
                                title = selectedCategory ?: "全部菜谱",
                                subtitle = if (searchQuery.isBlank()) {
                                    "按分类分组浏览，不搜索也能顺着目录往下翻。"
                                } else {
                                    "当前共找到 ${recipes.size} 道匹配菜谱。"
                                }
                            )
                        }
                        groupedRecipes.forEach { group ->
                            item(key = "group_${group.category}") {
                                CategoryGroupHeader(
                                    category = group.category,
                                    count = group.recipes.size
                                )
                            }
                            items(group.recipes, key = { it.id }) { recipe ->
                                RecipeCard(
                                    recipe = recipe,
                                    onClick = { onNavigateToRecipe(recipe.id) },
                                    onEdit = { onNavigateToAddRecipe(recipe.id) },
                                    onDelete = { showDeleteDialog = recipe },
                                    onToggleFavorite = { viewModel.toggleFavorite(recipe) }
                                )
                            }
                        }
                        item { Spacer(Modifier.height(88.dp)) }
                    }
                }
            }
        }
    }

    showDeleteDialog?.let { recipe ->
        AlertDialog(
            onDismissRequest = { showDeleteDialog = null },
            title = { Text("删除菜谱") },
            text = { Text("确定要删除「${recipe.name}」吗？") },
            confirmButton = {
                TextButton(onClick = { viewModel.deleteRecipe(recipe.id); showDeleteDialog = null }) {
                    Text("删除", color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton = { TextButton(onClick = { showDeleteDialog = null }) { Text("取消") } }
        )
    }

}

@Composable
private fun HomeCategoryDirectory(
    tabs: List<CategoryTabState>,
    onSelect: (String) -> Unit
) {
    val scrollState = rememberScrollState()
    Column {
        KitchenSectionTitle(
            title = "菜谱目录",
            subtitle = "先按分类缩小范围，再往下翻具体菜谱。"
        )
        Spacer(Modifier.height(10.dp))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .horizontalScroll(scrollState),
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            tabs.forEach { tab ->
                KitchenChip(
                    text = "${tab.label} ${tab.count}",
                    color = if (tab.selected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                    modifier = Modifier.clickable { onSelect(tab.label) },
                    leading = if (tab.selected) "选" else null
                )
            }
        }
    }
}

@Composable
private fun CategoryGroupHeader(
    category: String,
    count: Int
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        RecipeBadge(category)
        Spacer(Modifier.width(8.dp))
        Text(
            "$count 道",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ActiveCookingCard(
    recipe: RecipeEntity,
    onClick: () -> Unit,
    onClear: () -> Unit
) {
    KitchenCard(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    "正在做",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.primary
                )
                Spacer(Modifier.height(5.dp))
                Text(
                    recipe.name,
                    style = MaterialTheme.typography.titleMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    "点这里继续查看步骤和计时器",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            IconButton(onClick = onClear, modifier = Modifier.size(36.dp)) {
                Icon(Icons.Default.Close, contentDescription = "移除正在做", tint = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: RecipeEntity,
    onClick: () -> Unit,
    onEdit: () -> Unit,
    onDelete: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    var showMenu by remember { mutableStateOf(false) }

    RecipeGradientCard(
        category = recipe.category,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
    ) {
        Column(Modifier.padding(16.dp)) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        RecipeBadge(recipe.category)
                        if (recipe.isFavorite) {
                            Spacer(Modifier.width(6.dp))
                            KitchenChip("常做", color = MaterialTheme.colorScheme.primary, leading = "荐")
                        }
                    }
                    Spacer(Modifier.height(7.dp))
                    Text(
                        recipe.name,
                        style = MaterialTheme.typography.titleMedium,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    if (recipe.description.isNotBlank()) {
                        Spacer(Modifier.height(2.dp))
                        Text(
                            recipe.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                }
                Box {
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, "更多", tint = MaterialTheme.colorScheme.onSurfaceVariant)
                    }
                    DropdownMenu(expanded = showMenu, onDismissRequest = { showMenu = false }) {
                        DropdownMenuItem(
                            text = { Text("编辑") },
                            leadingIcon = { Icon(Icons.Default.Edit, null) },
                            onClick = { showMenu = false; onEdit() }
                        )
                        DropdownMenuItem(
                            text = { Text("删除", color = MaterialTheme.colorScheme.error) },
                            leadingIcon = { Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error) },
                            onClick = { showMenu = false; onDelete() }
                        )
                    }
                }
            }

            Spacer(Modifier.height(14.dp))
            Row(verticalAlignment = Alignment.CenterVertically) {
                KitchenChip("${recipe.servings} 人份", color = MaterialTheme.colorScheme.secondary)
                Spacer(Modifier.width(8.dp))
                KitchenChip("${recipe.totalTime} 分钟", color = MaterialTheme.colorScheme.tertiary)
                Spacer(Modifier.weight(1f))
                IconButton(onClick = onToggleFavorite, modifier = Modifier.size(36.dp)) {
                    Icon(
                        if (recipe.isFavorite) Icons.Default.Favorite else Icons.Default.FavoriteBorder,
                        contentDescription = "收藏",
                        tint = if (recipe.isFavorite) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Icon(Icons.Default.Timer, null, tint = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.size(18.dp))
            }
        }
    }
}
