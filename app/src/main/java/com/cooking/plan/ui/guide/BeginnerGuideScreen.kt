package com.cooking.plan.ui.guide

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Restaurant
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.cooking.plan.ui.components.KitchenBackground
import com.cooking.plan.ui.components.KitchenCard
import com.cooking.plan.ui.components.KitchenHero
import com.cooking.plan.ui.components.KitchenTopBar
import com.cooking.plan.ui.theme.CardShape

@Composable
fun BeginnerGuideScreen(onBack: () -> Unit) {
    Scaffold(
        topBar = {
            KitchenTopBar(
                title = "萌新厨房指引",
                subtitle = "名词、分量、调味和基础操作",
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    KitchenHero(
                        title = "先理解词，再动手做",
                        subtitle = "按少量多次、先备后炒的原则，新手也能把味道稳定下来。"
                    )
                }
                item {
                    GuideCard(
                        title = "厨房名词",
                        items = listOf(
                            GuideItem("少许", "用两三根手指捏起的量，常用于盐、胡椒粉和糖。"),
                            GuideItem("适量", "先少放，尝味后再补；2 人份可先从半勺盐或 1 勺生抽开始。"),
                            GuideItem("断生", "食材刚熟、没有生味，蔬菜颜色变亮但仍有脆感。"),
                            GuideItem("大火/中火/小火", "大火快速加热和收汁，中火稳定炒熟，小火慢炖或防糊底。")
                        )
                    )
                }
                item {
                    GuideCard(
                        title = "分量与调味",
                        items = listOf(
                            GuideItem("主食", "1 人份米饭约 1 碗，面条约 100-120g，粥用大米 40-60g。"),
                            GuideItem("家常菜", "2 人份肉类约 200-300g，蔬菜约 300-500g。"),
                            GuideItem("基础调味", "盐最后少量多次加；生抽负责咸鲜，老抽主要上色，蚝油负责鲜甜。"),
                            GuideItem("补救", "觉得淡先补盐或生抽，觉得咸可加水、配菜或少量糖平衡。")
                        )
                    )
                }
                item {
                    GuideCard(
                        title = "基础操作",
                        items = listOf(
                            GuideItem("焯水", "水开后放入食材短时间煮一下，用来去腥、去草酸或预熟；捞出后沥干再炒。"),
                            GuideItem("热锅", "空锅先加热到手靠近有明显热气，再放油，能减少粘锅。"),
                            GuideItem("爆香", "油热后先下葱姜蒜、辣椒等香料，闻到香味马上下主料，避免变苦。"),
                            GuideItem("收汁", "开大火让汤汁变少变浓，过程中翻动食材，防止糊底。")
                        )
                    )
                }
                item {
                    GuideCard(
                        title = "新手顺序",
                        items = listOf(
                            GuideItem("先备菜", "洗切、称量和调碗汁都放在开火前做完。"),
                            GuideItem("排顺序", "先做久炖菜，再做快炒菜，最后处理凉拌或摆盘。"),
                            GuideItem("稳定味道", "每次只改一个调味变量，记录自己喜欢的咸淡。")
                        )
                    )
                }
                item { Spacer(Modifier.height(16.dp)) }
            }
        }
    }
}

private data class GuideItem(
    val name: String,
    val description: String
)

@Composable
private fun GuideCard(title: String, items: List<GuideItem>) {
    KitchenCard(modifier = Modifier.fillMaxWidth()) {
        Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            items.forEach { item ->
                Row(verticalAlignment = Alignment.Top) {
                    Surface(
                        modifier = Modifier.size(34.dp),
                        shape = CardShape,
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                    ) {
                        Icon(
                            Icons.Default.Restaurant,
                            contentDescription = null,
                            modifier = Modifier.padding(8.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                    }
                    Column(Modifier.padding(start = 10.dp).weight(1f)) {
                        Text(
                            item.name,
                            style = MaterialTheme.typography.bodyMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Spacer(Modifier.height(2.dp))
                        Text(
                            item.description,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
        }
    }
}
