package com.cooking.plan.ui.onboarding

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
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
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.RestaurantMenu
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.theme.ChipShape
import com.cooking.plan.ui.theme.InputShape

data class OnboardingPage(
    val icon: ImageVector,
    val title: String,
    val description: String,
    val color: Color
)

private val pages = listOf(
    OnboardingPage(
        icon = Icons.AutoMirrored.Filled.List,
        title = "计划式菜谱",
        description = "每个菜谱都是一份下厨任务清单，完成一步勾一步。",
        color = Color(0xFFD94B34)
    ),
    OnboardingPage(
        icon = Icons.Default.CheckCircle,
        title = "智能计时器",
        description = "腌制、爆炒、炖煮等关键步骤可以直接开始计时。",
        color = Color(0xFF2E7D5B)
    ),
    OnboardingPage(
        icon = Icons.Default.SmartToy,
        title = "AI 生成菜谱",
        description = "输入菜名生成食材、步骤和计时器，再按口味微调。",
        color = Color(0xFFC08A20)
    )
)

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    val pagerState = rememberPagerState(pageCount = { pages.size })
    rememberCoroutineScope()

    KitchenBackground {
        Column(
            modifier = Modifier.fillMaxSize().padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween, verticalAlignment = Alignment.CenterVertically) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Surface(shape = InputShape, color = MaterialTheme.colorScheme.primaryContainer) {
                        Icon(
                            Icons.Default.RestaurantMenu,
                            null,
                            tint = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.padding(10.dp).size(22.dp)
                        )
                    }
                    Spacer(Modifier.width(10.dp))
                    Text("料理计划", style = MaterialTheme.typography.titleMedium)
                }
                if (pagerState.currentPage < pages.size - 1) {
                    TextButton(onClick = onFinish) {
                        Text("跳过")
                    }
                }
            }

            HorizontalPager(
                state = pagerState,
                modifier = Modifier.weight(1f)
            ) { page ->
                val p = pages[page]
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    KitchenCard(modifier = Modifier.fillMaxWidth()) {
                        Column(
                            modifier = Modifier.padding(28.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Surface(
                                modifier = Modifier.size(96.dp),
                                shape = InputShape,
                                color = p.color.copy(alpha = 0.12f),
                                border = BorderStroke(1.dp, p.color.copy(alpha = 0.22f))
                            ) {
                                Icon(p.icon, null, tint = p.color, modifier = Modifier.padding(24.dp))
                            }
                            Spacer(modifier = Modifier.height(24.dp))
                            Text(
                                p.title,
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                textAlign = TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(14.dp))
                            Text(
                                p.description,
                                style = MaterialTheme.typography.bodyLarge,
                                textAlign = TextAlign.Center,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                repeat(pages.size) { i ->
                    Box(
                        modifier = Modifier
                            .size(
                                width = if (i == pagerState.currentPage) 26.dp else 8.dp,
                                height = 8.dp
                            )
                            .background(
                                if (i == pagerState.currentPage) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.25f),
                                ChipShape
                            )
                    )
                    if (i < pages.size - 1) Spacer(modifier = Modifier.width(6.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            AnimatedVisibility(visible = pagerState.currentPage == pages.size - 1) {
                Button(
                    onClick = onFinish,
                    modifier = Modifier.fillMaxWidth().height(52.dp),
                    shape = ChipShape
                ) {
                    Text("开始使用", style = MaterialTheme.typography.titleMedium)
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
