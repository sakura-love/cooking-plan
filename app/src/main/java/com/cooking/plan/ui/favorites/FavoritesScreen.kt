package com.cooking.plan.ui.favorites

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
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.LocalDining
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenEmptyState
import com.cooking.plan.ui.components.KitchenHero
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.components.RecipeBadge
import com.cooking.plan.ui.components.recipeAccent
import com.cooking.plan.ui.theme.CardShape

@Composable
fun FavoritesScreen(
    onNavigateToRecipe: (Long) -> Unit,
    viewModel: FavoritesViewModel = hiltViewModel()
) {
    val favorites by viewModel.favorites.collectAsState()

    Scaffold(
        topBar = {
            KitchenTopBar(
                title = "收藏夹",
                subtitle = "${favorites.size} 道会反复做的菜"
            )
        },
        containerColor = androidx.compose.ui.graphics.Color.Transparent
    ) { padding ->
        KitchenBackground {
            if (favorites.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
                    KitchenEmptyState(
                        icon = Icons.Default.Favorite,
                        title = "还没有收藏菜谱",
                        subtitle = "在菜谱详情里点收藏，把顺手好做的菜留在这里。"
                    )
                }
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(padding).padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    item {
                        KitchenHero(
                            title = "常做菜单",
                            subtitle = "忙的时候从这里直接挑菜，不用重新翻找。"
                        )
                    }
                    items(favorites, key = { it.id }) { recipe ->
                        FavoriteCard(
                            recipe = recipe,
                            onClick = { onNavigateToRecipe(recipe.id) },
                            onRemove = { viewModel.removeFavorite(recipe) }
                        )
                    }
                    item { Spacer(modifier = Modifier.height(20.dp)) }
                }
            }
        }
    }
}

@Composable
private fun FavoriteCard(
    recipe: RecipeEntity,
    onClick: () -> Unit,
    onRemove: () -> Unit
) {
    val accent = recipeAccent(recipe.category)

    KitchenCard(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Surface(
                modifier = Modifier.size(48.dp),
                shape = CardShape,
                color = accent.copy(alpha = 0.14f),
                border = BorderStroke(1.dp, accent.copy(alpha = 0.22f))
            ) {
                Box(contentAlignment = Alignment.Center) {
                    Icon(Icons.Default.LocalDining, null, tint = accent, modifier = Modifier.size(24.dp))
                }
            }
            Spacer(modifier = Modifier.width(12.dp))
            Column(modifier = Modifier.weight(1f)) {
                RecipeBadge(recipe.category)
                Spacer(modifier = Modifier.height(7.dp))
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
            IconButton(onClick = onRemove) {
                Icon(Icons.Default.Favorite, "取消收藏", tint = MaterialTheme.colorScheme.primary)
            }
        }
    }
}
