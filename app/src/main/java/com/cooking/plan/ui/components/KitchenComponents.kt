package com.cooking.plan.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cooking.plan.ui.theme.CardShape
import com.cooking.plan.ui.theme.ChipShape

data class CategoryStyle(
    val label: String,
    val color: Color,
    val icon: String,
    val gradientColors: List<Color> = listOf(color, color.copy(alpha = 0.7f), color.copy(alpha = 0.4f))
)

val KitchenCategoryStyles = mapOf(
    "荤菜" to CategoryStyle(
        "荤菜", Color(0xFFBF3F2F), "肉",
        gradientColors = listOf(Color(0xFFBF3F2F), Color(0xFFE85D4A), Color(0xFFFF8A7A))
    ),
    "素菜" to CategoryStyle(
        "素菜", Color(0xFF2E7D5B), "蔬",
        gradientColors = listOf(Color(0xFF2E7D5B), Color(0xFF4CAF7A), Color(0xFF81C784))
    ),
    "汤类" to CategoryStyle(
        "汤类", Color(0xFF357E9C), "汤",
        gradientColors = listOf(Color(0xFF357E9C), Color(0xFF5DA3C4), Color(0xFF90CAF9))
    ),
    "主食" to CategoryStyle(
        "主食", Color(0xFFC08A20), "饭",
        gradientColors = listOf(Color(0xFFC08A20), Color(0xFFE6A832), Color(0xFFFFD54F))
    ),
    "甜品" to CategoryStyle(
        "甜品", Color(0xFFC85C87), "甜",
        gradientColors = listOf(Color(0xFFC85C87), Color(0xFFE07DA0), Color(0xFFF48FB1))
    ),
    "凉菜" to CategoryStyle(
        "凉菜", Color(0xFF248B88), "凉",
        gradientColors = listOf(Color(0xFF248B88), Color(0xFF4DB6AC), Color(0xFF80CBC4))
    ),
    "小吃" to CategoryStyle(
        "小吃", Color(0xFFC86E2C), "点",
        gradientColors = listOf(Color(0xFFC86E2C), Color(0xFFE88A4A), Color(0xFFFFAB91))
    )
)

@Composable
fun KitchenBackground(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        MaterialTheme.colorScheme.background,
                        MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.65f),
                        MaterialTheme.colorScheme.background
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
fun KitchenTopBar(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    navigation: @Composable (() -> Unit)? = null,
    action: @Composable (() -> Unit)? = null
) {
    Row(
        modifier = modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (navigation != null) {
            navigation()
            Spacer(Modifier.width(6.dp))
        }
        Column(Modifier.weight(1f)) {
            Text(
                title,
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onSurface
            )
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        if (action != null) action()
    }
}

@Composable
fun KitchenHero(
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    trailing: @Composable (() -> Unit)? = null
) {
    Card(
        modifier = modifier.fillMaxWidth(),
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface.copy(alpha = 0.72f)),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.75f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier.padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(Modifier.weight(1f)) {
                Text(
                    title,
                    style = MaterialTheme.typography.titleSmall,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(Modifier.height(2.dp))
                Text(
                    subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            if (trailing != null) {
                Spacer(Modifier.width(12.dp))
                trailing()
            }
        }
    }
}

@Composable
fun KitchenCard(
    modifier: Modifier = Modifier,
    emphasized: Boolean = false,
    highlightColor: Color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f),
    content: @Composable () -> Unit
) {
    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardDefaults.cardColors(
            containerColor = if (emphasized) {
                highlightColor
            } else {
                MaterialTheme.colorScheme.surface
            }
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = if (emphasized) 2.dp else 0.dp)
    ) {
        content()
    }
}

@Composable
fun KitchenChip(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary,
    leading: String? = null
) {
    Row(
        modifier = modifier
            .background(color.copy(alpha = 0.12f), ChipShape)
            .border(1.dp, color.copy(alpha = 0.18f), ChipShape)
            .padding(horizontal = 10.dp, vertical = 5.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (leading != null) {
            Text(
                leading,
                style = MaterialTheme.typography.labelSmall,
                color = color,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.width(5.dp))
        }
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            color = color
        )
    }
}

@Composable
fun KitchenSectionTitle(
    title: String,
    modifier: Modifier = Modifier,
    subtitle: String? = null
) {
    Column(modifier.fillMaxWidth()) {
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
        if (subtitle != null) {
            Spacer(Modifier.height(2.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
fun KitchenEmptyState(
    icon: ImageVector,
    title: String,
    subtitle: String,
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Surface(
            shape = CardShape,
            color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f),
            border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.18f))
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.padding(18.dp).size(34.dp)
            )
        }
        Spacer(Modifier.height(18.dp))
        Text(
            title,
            style = MaterialTheme.typography.titleMedium,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(6.dp))
        Text(
            subtitle,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
        if (action != null) {
            Spacer(Modifier.height(16.dp))
            action()
        }
    }
}

@Composable
fun RecipeBadge(category: String, modifier: Modifier = Modifier) {
    val style = KitchenCategoryStyles[category]
    KitchenChip(
        text = category.ifBlank { "未分类" },
        modifier = modifier,
        color = style?.color ?: MaterialTheme.colorScheme.secondary,
        leading = null
    )
}

fun recipeAccent(category: String): Color {
    return KitchenCategoryStyles[category]?.color ?: Color(0xFFD94B34)
}

fun recipeGradientColors(category: String): List<Color> {
    return KitchenCategoryStyles[category]?.gradientColors ?: listOf(
        Color(0xFFD94B34),
        Color(0xFFE85D4A),
        Color(0xFFFF8A7A)
    )
}

@Composable
fun RecipeGradientCard(
    category: String,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val gradientColors = recipeGradientColors(category)

    Card(
        modifier = modifier,
        shape = CardShape,
        colors = CardDefaults.cardColors(containerColor = Color.Transparent),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.8f)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    brush = Brush.linearGradient(
                        colors = gradientColors.map { it.copy(alpha = 0.15f) }
                    )
                )
        ) {
            // 高斯模糊效果层
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color.White.copy(alpha = 0.6f),
                                Color.White.copy(alpha = 0.3f),
                                Color.White.copy(alpha = 0.5f)
                            )
                        )
                    )
            )
            content()
        }
    }
}
