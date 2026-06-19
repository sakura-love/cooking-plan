package com.cooking.plan.data.local

internal object ExpandedDefaultRecipes {
    val all: List<DefaultRecipeSeed> = buildList {
        addAll(porkWokRecipes())
        addAll(chickenWokRecipes())
        addAll(beefAndLambWokRecipes())
        addAll(seafoodEggAndTofuRecipes())
        addAll(vegetableRecipes())
        addAll(signatureRecipes())
        addAll(soupRecipes())
        addAll(stapleRecipes())
        addAll(coldDishRecipes())
        addAll(snackAndDessertRecipes())
    }
}

private data class ProteinWokSpec(
    val name: String,
    val description: String,
    val totalTime: Int,
    val vegetables: List<DefaultIngredientSeed>,
    val sauce: List<DefaultIngredientSeed> = listOf(
        ingredient("生抽", "1", "勺"),
        ingredient("蚝油", "1", "勺")
    ),
    val aromatics: List<DefaultIngredientSeed> = listOf(
        ingredient("蒜末", "2", "瓣"),
        ingredient("姜丝", "1", "小撮")
    ),
    val proteinQuantity: String = "220",
    val servings: Int = 2,
    val finishNote: String = "看到食材刚好断生就关火，口感最嫩。"
)

private data class EggDishSpec(
    val name: String,
    val description: String,
    val totalTime: Int,
    val vegetables: List<DefaultIngredientSeed>,
    val eggs: String = "3",
    val aromatics: List<DefaultIngredientSeed> = listOf(ingredient("蒜末", "2", "瓣")),
    val sauce: List<DefaultIngredientSeed> = listOf(ingredient("盐", "适量")),
    val finishNote: String = "鸡蛋回锅后只要翻匀就出锅，避免炒老。"
)

private data class VegetableDishSpec(
    val name: String,
    val description: String,
    val totalTime: Int,
    val vegetables: List<DefaultIngredientSeed>,
    val aromatics: List<DefaultIngredientSeed> = listOf(ingredient("蒜末", "2", "瓣")),
    val seasonings: List<DefaultIngredientSeed> = listOf(
        ingredient("盐", "适量"),
        ingredient("蚝油", "1", "勺")
    ),
    val blanchSeconds: Int? = null,
    val finishNote: String = "翻匀后立刻装盘，保持蔬菜清爽口感。"
)

private fun porkWokRecipes(): List<DefaultRecipeSeed> = listOf(
    ProteinWokSpec("青椒肉丝", "脆嫩鲜香，是最稳的快手下饭菜之一", 18, listOf(ingredient("青椒", "2", "个"), ingredient("红椒", "半", "个"))),
    ProteinWokSpec("芹菜肉丝", "芹菜清香明显，越嚼越有滋味", 18, listOf(ingredient("芹菜", "3", "根"), ingredient("胡萝卜", "半", "根"))),
    ProteinWokSpec("蒜苔肉丝", "蒜香十足，适合配米饭和面条", 18, listOf(ingredient("蒜苔", "250", "g"), ingredient("红椒", "半", "个"))),
    ProteinWokSpec("洋葱肉片", "甜香明显，冰箱里常备洋葱就能做", 16, listOf(ingredient("洋葱", "1", "个"), ingredient("青椒", "1", "个"))),
    ProteinWokSpec("木耳肉片", "口感脆嫩，木耳吸汁很下饭", 18, listOf(ingredient("泡发木耳", "100", "g"), ingredient("黄瓜", "半", "根"))),
    ProteinWokSpec("杏鲍菇肉片", "菌香和肉香都很足，属于百搭家常菜", 18, listOf(ingredient("杏鲍菇", "2", "根"), ingredient("青椒", "1", "个"))),
    ProteinWokSpec("包菜肉片", "锅气足，整锅带着自然清甜", 15, listOf(ingredient("圆白菜", "300", "g"), ingredient("干辣椒", "3", "个")), sauce = listOf(ingredient("生抽", "1", "勺"), ingredient("香醋", "半", "勺"))),
    ProteinWokSpec("菜花肉片", "菜花吸足汤汁，咸香耐吃", 18, listOf(ingredient("菜花", "300", "g"), ingredient("胡萝卜", "半", "根"))),
    ProteinWokSpec("蒜苗肉片", "蒜苗辛香明显，很适合晚饭开胃", 16, listOf(ingredient("蒜苗", "4", "根"), ingredient("红椒", "半", "个"))),
    ProteinWokSpec("豆角肉丝", "豆角炒熟后特别香，属于夏天常做菜", 20, listOf(ingredient("豆角", "300", "g"), ingredient("红椒", "半", "个")), finishNote = "豆角必须完全炒熟再出锅，口感才会软而不生。"),
    ProteinWokSpec("香干肉丝", "豆香和肉香融合得很自然", 16, listOf(ingredient("香干", "4", "片"), ingredient("青椒", "1", "个"))),
    ProteinWokSpec("荷兰豆肉片", "配色清爽，属于招待客人也不丢分的快手菜", 16, listOf(ingredient("荷兰豆", "200", "g"), ingredient("胡萝卜", "半", "根"))),
    ProteinWokSpec("韭黄肉丝", "韭黄香气很足，特别适合拌饭", 15, listOf(ingredient("韭黄", "250", "g"), ingredient("红椒丝", "少许"))),
    ProteinWokSpec("莴笋肉片", "口感清脆，夏天吃很舒服", 17, listOf(ingredient("莴笋", "1", "根"), ingredient("木耳", "50", "g"))),
    ProteinWokSpec("西葫芦肉片", "清甜不腻，适合工作日快速开火", 16, listOf(ingredient("西葫芦", "1", "根"), ingredient("胡萝卜", "半", "根"))),
    ProteinWokSpec("青蒜肉丝", "青蒜味道足，做出来很有家常锅气", 16, listOf(ingredient("青蒜", "4", "根"), ingredient("豆腐干", "2", "片"))),
    ProteinWokSpec("平菇肉片", "平菇自带鲜味，少放调料也很好吃", 16, listOf(ingredient("平菇", "250", "g"), ingredient("青椒", "1", "个"))),
    ProteinWokSpec("榨菜肉丝", "咸鲜有层次，做面浇头也很合适", 14, listOf(ingredient("榨菜丝", "80", "g"), ingredient("青椒", "1", "个")), sauce = listOf(ingredient("生抽", "半", "勺"), ingredient("白糖", "半", "勺")))
).map { proteinWok(it, proteinName = "猪里脊", proteinCut = "切丝") }

private fun chickenWokRecipes(): List<DefaultRecipeSeed> = listOf(
    ProteinWokSpec("青椒鸡丁", "鸡肉嫩而不柴，调味简单也很下饭", 16, listOf(ingredient("青椒", "2", "个"), ingredient("红椒", "半", "个")), proteinQuantity = "260"),
    ProteinWokSpec("香菇鸡片", "香菇出汁后特别鲜，适合配热饭", 18, listOf(ingredient("鲜香菇", "6", "朵"), ingredient("青椒", "1", "个")), proteinQuantity = "260"),
    ProteinWokSpec("芹菜鸡丁", "芹菜脆、鸡丁嫩，吃起来很清爽", 17, listOf(ingredient("芹菜", "3", "根"), ingredient("胡萝卜", "半", "根")), proteinQuantity = "260"),
    ProteinWokSpec("洋葱鸡块", "洋葱甜味把鸡肉衬得更香", 18, listOf(ingredient("洋葱", "1", "个"), ingredient("青椒", "1", "个")), proteinQuantity = "280", finishNote = "鸡块炒到边缘微微焦香时出锅最有味道。"),
    ProteinWokSpec("荷兰豆鸡片", "颜色鲜亮，清爽不油腻", 16, listOf(ingredient("荷兰豆", "220", "g"), ingredient("胡萝卜", "半", "根")), proteinQuantity = "240"),
    ProteinWokSpec("黄瓜鸡丁", "黄瓜最后下锅，脆爽口感特别讨喜", 15, listOf(ingredient("黄瓜", "1", "根"), ingredient("腰果", "40", "g")), proteinQuantity = "240"),
    ProteinWokSpec("西兰花鸡片", "高蛋白又省事，健身期也很适合", 18, listOf(ingredient("西兰花", "1", "颗"), ingredient("胡萝卜", "半", "根")), proteinQuantity = "260"),
    ProteinWokSpec("山药鸡片", "山药软糯中带脆，特别适合家里老人孩子", 18, listOf(ingredient("山药", "250", "g"), ingredient("木耳", "60", "g")), proteinQuantity = "250"),
    ProteinWokSpec("菠萝鸡丁", "酸甜开胃，做法家庭化很容易成功", 20, listOf(ingredient("菠萝", "150", "g"), ingredient("青椒", "1", "个"), ingredient("红椒", "半", "个")), proteinQuantity = "260", sauce = listOf(ingredient("番茄酱", "1", "勺"), ingredient("白糖", "1", "勺"), ingredient("生抽", "半", "勺"))),
    ProteinWokSpec("腰果鸡丁", "口感丰富，招待客人也很体面", 18, listOf(ingredient("黄瓜", "半", "根"), ingredient("胡萝卜", "半", "根"), ingredient("腰果", "50", "g")), proteinQuantity = "260"),
    ProteinWokSpec("蒜苔鸡丁", "蒜苔脆香明显，配饭特别快", 18, listOf(ingredient("蒜苔", "250", "g"), ingredient("红椒", "半", "个")), proteinQuantity = "260"),
    ProteinWokSpec("木耳鸡片", "黑白分明，口感层次清晰", 18, listOf(ingredient("泡发木耳", "100", "g"), ingredient("黄瓜", "半", "根")), proteinQuantity = "250"),
    ProteinWokSpec("胡萝卜鸡丁", "甜口自然，很适合做儿童友好版晚餐", 16, listOf(ingredient("胡萝卜", "1", "根"), ingredient("玉米粒", "80", "g")), proteinQuantity = "250"),
    ProteinWokSpec("芦笋鸡片", "食材简单但很显清爽精致", 18, listOf(ingredient("芦笋", "200", "g"), ingredient("红椒", "半", "个")), proteinQuantity = "240")
).map { proteinWok(it, proteinName = "鸡胸肉", proteinCut = "切片") }

private fun beefAndLambWokRecipes(): List<DefaultRecipeSeed> = listOf(
    ProteinWokSpec("青椒牛肉丝", "典型家常快炒，越热越香", 18, listOf(ingredient("青椒", "2", "个"), ingredient("红椒", "半", "个")), proteinQuantity = "220"),
    ProteinWokSpec("洋葱牛肉", "洋葱甜香能把牛肉味衬得更足", 18, listOf(ingredient("洋葱", "1", "个"), ingredient("青椒", "1", "个")), proteinQuantity = "220"),
    ProteinWokSpec("芹菜牛肉", "清爽带筋道，是很稳的工作日晚饭", 18, listOf(ingredient("芹菜", "3", "根"), ingredient("红椒", "半", "个")), proteinQuantity = "220"),
    ProteinWokSpec("黑椒牛柳", "黑椒香气直接，配意面或米饭都行", 18, listOf(ingredient("洋葱", "1", "个"), ingredient("彩椒", "1", "个")), proteinQuantity = "240", sauce = listOf(ingredient("黑胡椒碎", "1", "小勺"), ingredient("生抽", "1", "勺"), ingredient("蚝油", "1", "勺"))),
    ProteinWokSpec("土豆牛肉片", "土豆吸汁后特别香，适合当一盘硬菜", 20, listOf(ingredient("土豆", "1", "个"), ingredient("青椒", "1", "个")), proteinQuantity = "240"),
    ProteinWokSpec("西兰花牛肉", "经典搭配，不容易失手", 18, listOf(ingredient("西兰花", "1", "颗"), ingredient("胡萝卜", "半", "根")), proteinQuantity = "240"),
    ProteinWokSpec("孜然牛肉", "孜然味浓，属于吃完还想再做的一类菜", 16, listOf(ingredient("洋葱", "半", "个"), ingredient("香菜", "1", "小把")), proteinQuantity = "240", sauce = listOf(ingredient("孜然粉", "1", "小勺"), ingredient("辣椒粉", "半", "小勺"), ingredient("生抽", "1", "勺"))),
    ProteinWokSpec("尖椒牛柳", "微辣开胃，适合重口味晚餐", 17, listOf(ingredient("尖椒", "3", "个"), ingredient("洋葱", "半", "个")), proteinQuantity = "240"),
    ProteinWokSpec("金针菇肥牛", "汤汁拌饭很香，十来分钟就能搞定", 16, listOf(ingredient("金针菇", "250", "g"), ingredient("洋葱", "半", "个")), proteinQuantity = "250", finishNote = "别把肥牛炒太久，卷边变色就足够。"),
    ProteinWokSpec("杏鲍菇牛肉", "菌香厚实，适合家常大火快炒", 18, listOf(ingredient("杏鲍菇", "2", "根"), ingredient("青椒", "1", "个")), proteinQuantity = "220"),
    ProteinWokSpec("菌菇牛肉", "鲜味很集中，不靠重调味也很好吃", 18, listOf(ingredient("白玉菇", "150", "g"), ingredient("蟹味菇", "150", "g")), proteinQuantity = "220"),
    ProteinWokSpec("菠菜牛肉", "带点汤汁拌饭很舒服，营养也均衡", 16, listOf(ingredient("菠菜", "250", "g"), ingredient("蒜片", "3", "瓣")), proteinQuantity = "220", aromatics = listOf(ingredient("蒜片", "3", "瓣"))),
    ProteinWokSpec("蒜苗牛肉", "蒜苗的辛香和牛肉特别搭", 16, listOf(ingredient("蒜苗", "4", "根"), ingredient("红椒", "半", "个")), proteinQuantity = "220"),
    ProteinWokSpec("孜然羊肉", "羊肉香气足，适合周末做一盘过瘾菜", 18, listOf(ingredient("洋葱", "半", "个"), ingredient("香菜", "1", "小把")), proteinQuantity = "240", sauce = listOf(ingredient("孜然粒", "1", "小勺"), ingredient("辣椒粉", "半", "小勺"), ingredient("生抽", "1", "勺")), finishNote = "看到锅里香气完全激发出来就可以装盘。")
).mapIndexed { index, spec ->
    val proteinName = if (index == 13) "羊后腿肉" else if (spec.name == "金针菇肥牛") "肥牛卷" else "牛里脊"
    val cut = if (spec.name.contains("丝")) "切丝" else "切片"
    proteinWok(spec, proteinName = proteinName, proteinCut = cut)
}

private fun seafoodEggAndTofuRecipes(): List<DefaultRecipeSeed> = buildList {
    addAll(
        listOf(
            ProteinWokSpec("西兰花虾仁", "清爽鲜甜，适合作为轻负担主菜", 16, listOf(ingredient("西兰花", "1", "颗"), ingredient("胡萝卜", "半", "根")), proteinQuantity = "220"),
            ProteinWokSpec("黄瓜虾仁", "黄瓜脆爽，虾仁只要炒到卷边就够", 14, listOf(ingredient("黄瓜", "1", "根"), ingredient("胡萝卜", "半", "根")), proteinQuantity = "220"),
            ProteinWokSpec("芦笋虾仁", "清甜又显精神，特别适合夏天", 16, listOf(ingredient("芦笋", "220", "g"), ingredient("红椒", "半", "个")), proteinQuantity = "220"),
            ProteinWokSpec("番茄虾仁", "带少量汤汁，拌饭特别稳", 15, listOf(ingredient("番茄", "2", "个"), ingredient("洋葱", "半", "个")), proteinQuantity = "220", sauce = listOf(ingredient("番茄酱", "1", "勺"), ingredient("盐", "适量"))),
            ProteinWokSpec("青豆虾仁", "颗粒感丰富，很适合做家常便当菜", 15, listOf(ingredient("青豆", "100", "g"), ingredient("玉米粒", "80", "g"), ingredient("胡萝卜丁", "半", "根")), proteinQuantity = "220"),
            ProteinWokSpec("木耳虾仁", "脆嫩鲜弹，口感很利落", 16, listOf(ingredient("泡发木耳", "100", "g"), ingredient("黄瓜", "半", "根")), proteinQuantity = "220")
        ).map { proteinWok(it, proteinName = "虾仁", proteinCut = "去虾线后擦干") }
    )
    addAll(
        listOf(
            EggDishSpec("韭菜炒鸡蛋", "最省事也最稳定的家常蛋菜之一", 10, listOf(ingredient("韭菜", "200", "g")), aromatics = emptyList()),
            EggDishSpec("青椒炒鸡蛋", "微辣开胃，很适合早餐和晚饭都做", 10, listOf(ingredient("青椒", "2", "个")), aromatics = listOf(ingredient("葱花", "适量"))),
            EggDishSpec("洋葱炒蛋", "洋葱炒软以后特别甜，孩子也容易接受", 10, listOf(ingredient("洋葱", "1", "个")), aromatics = listOf(ingredient("葱花", "适量"))),
            EggDishSpec("苦瓜炒蛋", "先把苦瓜处理好，吃起来只有清苦没有生涩", 12, listOf(ingredient("苦瓜", "1", "根")), finishNote = "苦瓜保持一点脆感最好吃。"),
            EggDishSpec("黄瓜炒蛋", "简单清甜，适合夏天没胃口时做", 10, listOf(ingredient("黄瓜", "1", "根")), aromatics = listOf(ingredient("蒜末", "1", "瓣"))),
            EggDishSpec("木耳炒蛋", "木耳脆、鸡蛋香，搭配很经典", 12, listOf(ingredient("泡发木耳", "100", "g"), ingredient("胡萝卜", "半", "根"))),
            EggDishSpec("丝瓜炒蛋", "丝瓜出汁后特别鲜甜，属于夏季常备菜", 10, listOf(ingredient("丝瓜", "1", "根")), aromatics = listOf(ingredient("蒜末", "1", "瓣"))),
            EggDishSpec("虾仁滑蛋", "口感软嫩，很适合做给老人孩子", 12, listOf(ingredient("虾仁", "120", "g"), ingredient("葱花", "适量")), eggs = "4", aromatics = emptyList())
        ).map { eggDish(it) }
    )
    addAll(
        listOf(
            recipe(
                name = "家常豆腐",
                category = "素菜",
                description = "豆腐外香内嫩，酱汁裹得很到位",
                servings = 2,
                totalTime = 18,
                ingredients = mergeIngredients(
                    listOf(ingredient("北豆腐", "1", "块")),
                    listOf(ingredient("青椒", "1", "个"), ingredient("木耳", "60", "g"), ingredient("胡萝卜", "半", "根")),
                    listOf(ingredient("豆瓣酱", "1", "勺"), ingredient("生抽", "1", "勺"), ingredient("蒜末", "2", "瓣"), ingredient("淀粉", "1", "小勺"))
                ),
                steps = steps(
                    step("豆腐切三角块，用厨房纸吸掉表面水分"),
                    step("青椒切块，木耳泡发撕小朵，胡萝卜切片"),
                    step("取小碗调好生抽、豆瓣酱、淀粉和半碗清水"),
                    step("锅里稍多放一点油，把豆腐煎到两面金黄后盛出", 180, "STIR_FRY"),
                    step("留底油爆香蒜末，下胡萝卜和木耳先翻炒", 60, "STIR_FRY"),
                    step("放入青椒和煎好的豆腐，沿锅边倒入碗汁", 60, "STIR_FRY"),
                    step("轻轻翻匀，等酱汁裹住豆腐表面就出锅", 45, "STIR_FRY")
                )
            ),
            recipe(
                name = "番茄豆腐",
                category = "素菜",
                description = "酸甜柔和，带一点汤汁特别拌饭",
                servings = 2,
                totalTime = 15,
                ingredients = ingredients(
                    ingredient("嫩豆腐", "1", "盒"),
                    ingredient("番茄", "2", "个"),
                    ingredient("蒜末", "2", "瓣"),
                    ingredient("生抽", "1", "勺"),
                    ingredient("白糖", "半", "勺"),
                    ingredient("盐", "适量")
                ),
                steps = steps(
                    step("番茄切块，豆腐切大块后放淡盐水里泡一会"),
                    step("锅中少油爆香蒜末，下番茄炒到明显出汁", 120, "STIR_FRY"),
                    step("加生抽、白糖和半碗热水，煮成基础汤汁", 120, "STEW"),
                    step("放入豆腐，小火轻轻推动，让豆腐均匀入味", 180, "STEW"),
                    step("最后尝味补盐，留一点汤汁最适合拌饭")
                )
            ),
            recipe(
                name = "青椒豆皮",
                category = "素菜",
                description = "豆香明显，越嚼越香，很适合当常备快手菜",
                servings = 2,
                totalTime = 12,
                ingredients = ingredients(
                    ingredient("豆皮", "2", "张"),
                    ingredient("青椒", "2", "个"),
                    ingredient("蒜末", "2", "瓣"),
                    ingredient("生抽", "1", "勺"),
                    ingredient("盐", "适量")
                ),
                steps = steps(
                    step("豆皮切宽条，青椒去籽切丝"),
                    step("锅中烧水，把豆皮快速焯一下去豆腥味", 40, "BLANCH"),
                    step("热锅下油爆香蒜末", 20, "STIR_FRY"),
                    step("先下青椒炒到断生，再放入豆皮", 60, "STIR_FRY"),
                    step("加生抽和少许盐翻匀，豆皮热透后出锅", 45, "STIR_FRY")
                )
            ),
            recipe(
                name = "芹菜香干",
                category = "素菜",
                description = "清香、脆爽、豆香兼具，非常适合工作日晚饭",
                servings = 2,
                totalTime = 12,
                ingredients = ingredients(
                    ingredient("芹菜", "3", "根"),
                    ingredient("香干", "4", "片"),
                    ingredient("蒜末", "2", "瓣"),
                    ingredient("红椒", "半", "个"),
                    ingredient("生抽", "1", "勺")
                ),
                steps = steps(
                    step("芹菜去筋切段，香干切条，红椒切丝"),
                    step("锅里烧开水，把芹菜焯到颜色变亮后捞出", 30, "BLANCH"),
                    step("热锅少油爆香蒜末", 20, "STIR_FRY"),
                    step("下香干先炒到边缘略微起泡", 60, "STIR_FRY"),
                    step("放入芹菜和红椒，加生抽快速翻匀后出锅", 45, "STIR_FRY")
                )
            )
        )
    )
}

private fun vegetableRecipes(): List<DefaultRecipeSeed> = listOf(
    VegetableDishSpec("清炒油麦菜", "蒜香清爽，五分钟就能出锅", 8, listOf(ingredient("油麦菜", "300", "g"))),
    VegetableDishSpec("蚝油生菜", "生菜保持脆嫩，酱香不抢味", 8, listOf(ingredient("生菜", "1", "颗")), seasonings = listOf(ingredient("蚝油", "1", "勺"), ingredient("盐", "少许"))),
    VegetableDishSpec("蒜蓉空心菜", "锅气越足越好吃，适合夏天", 10, listOf(ingredient("空心菜", "350", "g")), finishNote = "空心菜炒软一点就行，不要久焖。"),
    VegetableDishSpec("清炒上海青", "最简单也最能检验火候的青菜", 8, listOf(ingredient("上海青", "300", "g"))),
    VegetableDishSpec("蒜蓉娃娃菜", "汤汁鲜甜，适合配粥和米饭", 10, listOf(ingredient("娃娃菜", "2", "棵")), seasonings = listOf(ingredient("盐", "适量"), ingredient("生抽", "半", "勺"))),
    VegetableDishSpec("清炒菜心", "菜心脆嫩，火候对了就特别香", 8, listOf(ingredient("菜心", "300", "g"))),
    VegetableDishSpec("青椒土豆片", "脆中带粉，属于很稳的家常菜", 12, listOf(ingredient("土豆", "2", "个"), ingredient("青椒", "2", "个")), seasonings = listOf(ingredient("盐", "适量"), ingredient("生抽", "半", "勺"), ingredient("香醋", "半", "勺"))),
    VegetableDishSpec("干煸豆角", "先把豆角煸到起皱才是关键", 18, listOf(ingredient("豆角", "350", "g"), ingredient("肉末", "60", "g"), ingredient("芽菜", "1", "勺")), aromatics = listOf(ingredient("蒜末", "2", "瓣"), ingredient("干辣椒", "3", "个")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("盐", "少许")), finishNote = "豆角完全熟透再出锅，香气会更足。"),
    VegetableDishSpec("地三鲜", "土豆、茄子、青椒都挂上汁，很有满足感", 20, listOf(ingredient("土豆", "1", "个"), ingredient("茄子", "1", "根"), ingredient("青椒", "1", "个")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("蚝油", "1", "勺"), ingredient("白糖", "半", "勺"))),
    VegetableDishSpec("西红柿炒菜花", "酸甜开胃，菜花吸汁后特别香", 15, listOf(ingredient("菜花", "300", "g"), ingredient("番茄", "2", "个")), seasonings = listOf(ingredient("盐", "适量"), ingredient("番茄酱", "1", "勺"))),
    VegetableDishSpec("干锅花菜", "边缘炒到微焦时最好吃", 18, listOf(ingredient("花菜", "350", "g"), ingredient("五花肉", "80", "g"), ingredient("青椒", "1", "个")), aromatics = listOf(ingredient("蒜片", "3", "瓣"), ingredient("干辣椒", "3", "个")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("蚝油", "1", "勺")), finishNote = "锅里略干一点更有干锅香气。"),
    VegetableDishSpec("虎皮青椒", "青椒先煎起皱，香味一下子就出来了", 12, listOf(ingredient("青椒", "6", "个")), aromatics = listOf(ingredient("蒜末", "2", "瓣")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("香醋", "1", "勺"), ingredient("白糖", "半", "勺"))),
    VegetableDishSpec("木耳山药", "清爽脆嫩，很适合作为一桌里的平衡菜", 15, listOf(ingredient("山药", "250", "g"), ingredient("泡发木耳", "100", "g"), ingredient("胡萝卜", "半", "根"))),
    VegetableDishSpec("蒜蓉茄子", "蒜香足，茄子软糯不油腻", 18, listOf(ingredient("长茄子", "2", "根")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("蚝油", "1", "勺"), ingredient("小葱", "适量")), finishNote = "茄子软透、蒜香完全融合时口感最好。"),
    VegetableDishSpec("红烧茄子", "外层微糯，酱汁浓而不齁", 18, listOf(ingredient("茄子", "2", "根"), ingredient("青椒", "1", "个")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("老抽", "半", "勺"), ingredient("白糖", "半", "勺"), ingredient("淀粉", "1", "小勺"))),
    VegetableDishSpec("韭菜香干", "韭菜香很明显，属于怎么做都香的一道菜", 12, listOf(ingredient("韭菜", "200", "g"), ingredient("香干", "4", "片")), seasonings = listOf(ingredient("盐", "适量"), ingredient("生抽", "半", "勺"))),
    VegetableDishSpec("清炒南瓜", "带自然甜味，老人孩子都容易接受", 15, listOf(ingredient("南瓜", "300", "g"), ingredient("青椒", "半", "个")), seasonings = listOf(ingredient("盐", "适量"), ingredient("白糖", "少许"))),
    VegetableDishSpec("清炒藕片", "脆嫩爽口，吃起来很清口", 12, listOf(ingredient("莲藕", "1", "节"), ingredient("青椒", "1", "个")), seasonings = listOf(ingredient("盐", "适量"), ingredient("香醋", "半", "勺"))),
    VegetableDishSpec("玉米胡萝卜豌豆", "颜色鲜亮，做便当很合适", 10, listOf(ingredient("玉米粒", "100", "g"), ingredient("豌豆", "80", "g"), ingredient("胡萝卜丁", "半", "根")), seasonings = listOf(ingredient("盐", "适量"), ingredient("白糖", "少许"))),
    VegetableDishSpec("木耳腐竹", "泡发好后特别入味，是很实用的素菜", 16, listOf(ingredient("泡发木耳", "100", "g"), ingredient("泡发腐竹", "180", "g"), ingredient("青椒", "1", "个")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("蚝油", "1", "勺"))),
    VegetableDishSpec("素炒三丝", "胡萝卜、土豆和青椒一起炒，很家常", 12, listOf(ingredient("土豆", "1", "个"), ingredient("胡萝卜", "1", "根"), ingredient("青椒", "1", "个")), seasonings = listOf(ingredient("盐", "适量"), ingredient("香醋", "半", "勺"))),
    VegetableDishSpec("炒合菜", "豆芽、韭菜和鸡蛋一起下锅，特别有锅气", 14, listOf(ingredient("豆芽", "250", "g"), ingredient("韭菜", "150", "g"), ingredient("鸡蛋", "2", "个")), seasonings = listOf(ingredient("生抽", "半", "勺"), ingredient("盐", "适量"))),
    VegetableDishSpec("蒜香苋菜", "苋菜炒好会出一点红汁，特别下饭", 8, listOf(ingredient("苋菜", "300", "g"))),
    VegetableDishSpec("香菇油菜", "油菜清脆、香菇鲜甜，适合配任何主菜", 12, listOf(ingredient("油菜", "300", "g"), ingredient("鲜香菇", "6", "朵"))),
    VegetableDishSpec("西蓝花口蘑", "鲜味集中，做出来很显干净利落", 14, listOf(ingredient("西兰花", "1", "颗"), ingredient("口蘑", "8", "个"))),
    VegetableDishSpec("蒜香菠菜", "菠菜焯过再炒，口感和颜色都更稳", 10, listOf(ingredient("菠菜", "300", "g")), blanchSeconds = 30, seasonings = listOf(ingredient("盐", "适量"), ingredient("香油", "几滴"))),
    VegetableDishSpec("酸豆角土豆丁", "酸香开胃，属于很能下饭的小盘菜", 14, listOf(ingredient("土豆", "1", "个"), ingredient("酸豆角", "100", "g"), ingredient("肉末", "50", "g")), aromatics = listOf(ingredient("蒜末", "2", "瓣"), ingredient("干辣椒", "2", "个")), seasonings = listOf(ingredient("生抽", "1", "勺"))),
    VegetableDishSpec("芦笋口蘑", "口感清爽，适合想吃得轻一点的时候", 12, listOf(ingredient("芦笋", "220", "g"), ingredient("口蘑", "8", "个"))),
    VegetableDishSpec("山药木耳胡萝卜", "三色搭配看着舒服，也很适合老人吃", 14, listOf(ingredient("山药", "250", "g"), ingredient("木耳", "80", "g"), ingredient("胡萝卜", "半", "根"))),
    VegetableDishSpec("娃娃菜粉丝", "粉丝吸汁后很香，适合搭配清淡主菜", 14, listOf(ingredient("娃娃菜", "2", "棵"), ingredient("粉丝", "1", "小把")), seasonings = listOf(ingredient("生抽", "1", "勺"), ingredient("蚝油", "1", "勺"))),
    VegetableDishSpec("茄汁豆角", "豆角带点番茄酸甜口，很适合换换做法", 16, listOf(ingredient("豆角", "300", "g"), ingredient("番茄", "1", "个")), seasonings = listOf(ingredient("番茄酱", "1", "勺"), ingredient("盐", "适量"), ingredient("白糖", "半", "勺")), finishNote = "豆角完全熟透、汤汁微收时最合适。")
).map { vegetableDish(it) }

private fun signatureRecipes(): List<DefaultRecipeSeed> = listOf(
    recipe(
        name = "红烧肉",
        category = "荤菜",
        description = "肥而不腻，酱香浓郁，属于值得慢慢收汁的一锅菜",
        servings = 3,
        totalTime = 60,
        ingredients = ingredients(
            ingredient("五花肉", "600", "g"),
            ingredient("冰糖", "30", "g"),
            ingredient("姜片", "4", "片"),
            ingredient("料酒", "2", "勺"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "1", "勺"),
            ingredient("八角", "2", "个")
        ),
        steps = steps(
            step("五花肉切大块，冷水下锅焯去血沫后捞出", 240, "BLANCH"),
            step("锅里少油，下五花肉煸到表面微黄、逼出多余油脂", 300, "STIR_FRY"),
            step("盛出部分油，只留底油把冰糖慢慢炒到琥珀色", 90, "STIR_FRY"),
            step("倒回五花肉翻匀，让每块肉都裹上糖色", 60, "STIR_FRY"),
            step("加入姜片、料酒、生抽、老抽和八角炒香", 60, "STIR_FRY"),
            step("倒入热水没过肉块，小火慢炖到软糯", 2400, "STEW"),
            step("最后开大火收汁，看到汤汁发亮能挂住肉块时关火", 240, "STIR_FRY")
        )
    ),
    recipe(
        name = "糖醋里脊",
        category = "荤菜",
        description = "外酥里嫩，酸甜汁裹得均匀，家庭版也能很稳",
        servings = 2,
        totalTime = 25,
        ingredients = ingredients(
            ingredient("猪里脊", "300", "g"),
            ingredient("鸡蛋", "1", "个"),
            ingredient("淀粉", "4", "勺"),
            ingredient("番茄酱", "2", "勺"),
            ingredient("白糖", "2", "勺"),
            ingredient("香醋", "1", "勺")
        ),
        steps = steps(
            step("里脊切条，加少许盐、鸡蛋和淀粉抓成能挂浆的状态"),
            step("取小碗调好番茄酱、白糖、香醋和半碗清水"),
            step("锅里烧到六成热，把里脊条分次炸到定型捞出", 240, "STIR_FRY"),
            step("油温升高后复炸一次，炸到外壳更酥脆", 90, "STIR_FRY"),
            step("另起锅倒入糖醋汁，小火熬到略微浓稠", 90, "STIR_FRY"),
            step("把里脊倒回锅中快速翻匀，确保每根都裹上酱汁", 30, "STIR_FRY"),
            step("酱汁刚好包裹住外皮时立刻出锅")
        )
    ),
    recipe(
        name = "黄焖鸡",
        category = "荤菜",
        description = "鸡块入味、土豆软糯，汤汁拌饭尤其过瘾",
        servings = 3,
        totalTime = 40,
        ingredients = ingredients(
            ingredient("鸡腿块", "700", "g"),
            ingredient("土豆", "1", "个"),
            ingredient("青椒", "1", "个"),
            ingredient("香菇", "6", "朵"),
            ingredient("姜片", "4", "片"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "半", "勺")
        ),
        steps = steps(
            step("鸡腿块洗净沥干，土豆切块，香菇切半，青椒切段"),
            step("热锅少油，先把鸡块煎到表面微黄", 240, "STIR_FRY"),
            step("加入姜片翻香，再下生抽和老抽上色", 45, "STIR_FRY"),
            step("放入香菇和土豆翻匀，倒入热水没过食材", 60, "STEW"),
            step("盖盖小火焖到鸡块熟透、土豆变软", 1200, "STEW"),
            step("最后放青椒再焖一分钟，尝味后即可出锅", 60, "STEW")
        )
    ),
    recipe(
        name = "啤酒鸭",
        category = "荤菜",
        description = "鸭肉越焖越香，带一点麦香却不会腥",
        servings = 3,
        totalTime = 55,
        ingredients = ingredients(
            ingredient("鸭块", "700", "g"),
            ingredient("啤酒", "1", "罐"),
            ingredient("姜片", "5", "片"),
            ingredient("蒜瓣", "4", "瓣"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "半", "勺"),
            ingredient("八角", "2", "个")
        ),
        steps = steps(
            step("鸭块冷水下锅焯一下，洗净表面血沫", 240, "BLANCH"),
            step("锅里不放油，把鸭块下锅煸出部分油脂", 300, "STIR_FRY"),
            step("加入姜片、蒜瓣和八角炒香", 60, "STIR_FRY"),
            step("放入生抽、老抽翻匀后倒入整罐啤酒", 60, "STEW"),
            step("啤酒烧开后转小火慢焖到鸭肉变软", 2100, "STEW"),
            step("最后开盖收汁，保留少许汤汁最适合拌饭", 180, "STIR_FRY")
        )
    ),
    recipe(
        name = "土豆烧牛肉",
        category = "荤菜",
        description = "牛肉酥而不散，土豆吸满汤汁特别顶饱",
        servings = 3,
        totalTime = 70,
        ingredients = ingredients(
            ingredient("牛腩", "700", "g"),
            ingredient("土豆", "2", "个"),
            ingredient("胡萝卜", "1", "根"),
            ingredient("姜片", "4", "片"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "1", "勺"),
            ingredient("八角", "2", "个")
        ),
        steps = steps(
            step("牛腩切块后冷水下锅焯净血沫", 240, "BLANCH"),
            step("土豆和胡萝卜切滚刀块，清水泡着备用"),
            step("锅里少油炒香姜片和八角，下牛腩翻炒到表面收紧", 240, "STIR_FRY"),
            step("加入生抽、老抽炒匀上色", 45, "STIR_FRY"),
            step("倒入热水没过牛腩，小火先炖到牛肉七八成熟", 2700, "STEW"),
            step("放入土豆和胡萝卜继续焖到软糯入味", 900, "STEW"),
            step("最后收一下汁，汤汁能轻轻裹住土豆就可以")
        )
    ),
    recipe(
        name = "红烧牛腩",
        category = "荤菜",
        description = "汤汁醇厚，适合搭米饭也适合拌面",
        servings = 3,
        totalTime = 80,
        ingredients = ingredients(
            ingredient("牛腩", "700", "g"),
            ingredient("姜片", "4", "片"),
            ingredient("大葱", "1", "段"),
            ingredient("料酒", "2", "勺"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "1", "勺"),
            ingredient("冰糖", "20", "g")
        ),
        steps = steps(
            step("牛腩切大块，冷水下锅焯净后捞出", 240, "BLANCH"),
            step("锅里少油炒化冰糖到浅焦色", 60, "STIR_FRY"),
            step("下牛腩翻到表面都裹上颜色", 120, "STIR_FRY"),
            step("加入姜片、大葱、料酒、生抽和老抽炒香", 60, "STIR_FRY"),
            step("倒热水没过牛腩，小火慢炖到牛肉酥软", 3300, "STEW"),
            step("大火略收汁，留一点拌饭汤更香", 180, "STIR_FRY")
        )
    ),
    recipe(
        name = "咖喱鸡块",
        category = "荤菜",
        description = "咖喱味浓而不呛，土豆胡萝卜都很入味",
        servings = 3,
        totalTime = 35,
        ingredients = ingredients(
            ingredient("鸡腿块", "600", "g"),
            ingredient("土豆", "2", "个"),
            ingredient("胡萝卜", "1", "根"),
            ingredient("洋葱", "1", "个"),
            ingredient("咖喱块", "4", "块")
        ),
        steps = steps(
            step("鸡腿块洗净沥干，土豆胡萝卜切块，洋葱切片"),
            step("锅里少油先把洋葱炒软炒香", 120, "STIR_FRY"),
            step("加入鸡块翻炒到表面变色", 180, "STIR_FRY"),
            step("倒入土豆和胡萝卜一起翻匀", 60, "STIR_FRY"),
            step("加入热水没过食材，小火煮到土豆接近变软", 900, "STEW"),
            step("放入咖喱块慢慢搅开，再小火煮到汤汁顺滑", 480, "STEW"),
            step("最后尝味即可，通常不需要再额外加盐")
        )
    ),
    recipe(
        name = "咖喱牛腩",
        category = "荤菜",
        description = "比咖喱鸡更厚重，适合周末炖一锅慢慢吃",
        servings = 3,
        totalTime = 80,
        ingredients = ingredients(
            ingredient("牛腩", "700", "g"),
            ingredient("土豆", "2", "个"),
            ingredient("胡萝卜", "1", "根"),
            ingredient("洋葱", "1", "个"),
            ingredient("咖喱块", "4", "块")
        ),
        steps = steps(
            step("牛腩焯水洗净，土豆胡萝卜切块，洋葱切片"),
            step("先把牛腩加热水炖到八成熟", 3000, "STEW"),
            step("另起锅把洋葱炒香，加入土豆胡萝卜翻匀", 180, "STIR_FRY"),
            step("倒入牛腩和牛肉汤，继续炖到蔬菜变软", 900, "STEW"),
            step("放入咖喱块，小火煮到汤汁浓稠顺滑", 480, "STEW"),
            step("关火前轻轻翻匀，避免土豆被搅碎")
        )
    ),
    recipe(
        name = "香菇炖鸡",
        category = "荤菜",
        description = "鲜香温和，汤和肉都值得留着拌饭",
        servings = 3,
        totalTime = 45,
        ingredients = ingredients(
            ingredient("鸡腿块", "700", "g"),
            ingredient("干香菇", "10", "朵"),
            ingredient("姜片", "4", "片"),
            ingredient("生抽", "2", "勺"),
            ingredient("料酒", "1", "勺")
        ),
        steps = steps(
            step("干香菇提前泡发，泡香菇的水留着沉淀备用"),
            step("鸡块焯水后洗净沥干", 240, "BLANCH"),
            step("锅里少油炒香姜片，下鸡块翻到表面略微发紧", 240, "STIR_FRY"),
            step("加料酒、生抽和香菇一起翻匀", 60, "STIR_FRY"),
            step("倒入香菇水和适量热水，小火炖到鸡肉熟透", 1500, "STEW"),
            step("出锅前尝味，喜欢的话撒一点葱花")
        )
    ),
    recipe(
        name = "冬瓜烧排骨",
        category = "荤菜",
        description = "冬瓜吸了肉汁却不腻，特别适合夏天晚饭",
        servings = 3,
        totalTime = 45,
        ingredients = ingredients(
            ingredient("排骨", "500", "g"),
            ingredient("冬瓜", "400", "g"),
            ingredient("姜片", "4", "片"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "半", "勺")
        ),
        steps = steps(
            step("排骨焯水洗净，冬瓜去皮切大块", 240, "BLANCH"),
            step("锅里少油炒香姜片，下排骨翻炒到表面收紧", 240, "STIR_FRY"),
            step("加入生抽和老抽炒匀上色", 45, "STIR_FRY"),
            step("倒入热水没过排骨，小火先炖二十五分钟", 1500, "STEW"),
            step("放入冬瓜继续焖到冬瓜透明发软", 600, "STEW"),
            step("最后根据汤汁多少决定是否开盖略收一下")
        )
    ),
    recipe(
        name = "红烧鲫鱼",
        category = "荤菜",
        description = "煎透再烧是关键，成品咸鲜下饭",
        servings = 2,
        totalTime = 30,
        ingredients = ingredients(
            ingredient("鲫鱼", "1", "条"),
            ingredient("姜片", "4", "片"),
            ingredient("葱段", "适量"),
            ingredient("生抽", "2", "勺"),
            ingredient("老抽", "半", "勺"),
            ingredient("料酒", "1", "勺")
        ),
        steps = steps(
            step("鲫鱼清理干净，擦干表面水分并在两面划刀"),
            step("锅烧热后下油，把鲫鱼煎到两面定型金黄", 360, "STIR_FRY"),
            step("加入姜片和葱段，沿锅边烹入料酒", 30, "STIR_FRY"),
            step("放入生抽、老抽和半碗热水，转小火烧入味", 480, "STEW"),
            step("中途把汤汁不断淋到鱼身上，帮助更均匀入味"),
            step("最后开盖把汤汁略收浓即可")
        )
    ),
    recipe(
        name = "清蒸鲈鱼",
        category = "荤菜",
        description = "做法不复杂，但每一步都影响鱼肉嫩度",
        servings = 2,
        totalTime = 22,
        ingredients = ingredients(
            ingredient("鲈鱼", "1", "条"),
            ingredient("姜丝", "适量"),
            ingredient("葱丝", "适量"),
            ingredient("蒸鱼豉油", "2", "勺"),
            ingredient("食用油", "2", "勺")
        ),
        steps = steps(
            step("鲈鱼清理干净，在鱼身两侧划刀，鱼肚里塞一点姜丝"),
            step("盘底垫几片姜，放上鲈鱼，再铺少量姜丝去腥"),
            step("蒸锅水开后再上鱼，大火蒸八到十分钟", 600, "STEW"),
            step("蒸好后倒掉盘里的腥水，挑掉大部分旧姜丝"),
            step("铺上新鲜葱丝，淋蒸鱼豉油"),
            step("另起锅把油烧热，趁热泼在葱丝上激出香味", 30, "STIR_FRY")
        )
    ),
    recipe(
        name = "豆豉蒸排骨",
        category = "荤菜",
        description = "蒸出来软嫩咸香，特别适合天热时少开火",
        servings = 2,
        totalTime = 35,
        ingredients = ingredients(
            ingredient("排骨", "350", "g"),
            ingredient("豆豉", "1", "勺"),
            ingredient("蒜末", "2", "瓣"),
            ingredient("生抽", "1", "勺"),
            ingredient("淀粉", "1", "小勺"),
            ingredient("料酒", "1", "勺")
        ),
        steps = steps(
            step("排骨洗净后泡去血水，再沥干"),
            step("加豆豉、蒜末、生抽、料酒和淀粉抓匀，腌二十分钟", 1200, "MARINATE"),
            step("把排骨平铺到浅盘里，不要堆得太厚"),
            step("蒸锅水开后上锅，大火蒸二十分钟左右", 1200, "STEW"),
            step("关火后焖两分钟再开盖，肉会更稳更嫩")
        )
    ),
    recipe(
        name = "蒜蓉粉丝虾",
        category = "荤菜",
        description = "蒜香、虾鲜和粉丝吸汁同时到位的一道菜",
        servings = 2,
        totalTime = 20,
        ingredients = ingredients(
            ingredient("大虾", "10", "只"),
            ingredient("粉丝", "1", "把"),
            ingredient("蒜末", "6", "瓣"),
            ingredient("生抽", "2", "勺"),
            ingredient("小葱", "适量")
        ),
        steps = steps(
            step("粉丝用温水泡软，大虾开背去虾线"),
            step("蒜末分成两份，一份热油炒香做熟蒜，一份保留生蒜味"),
            step("把泡软的粉丝铺盘底，摆上大虾，撒上两种蒜末"),
            step("淋上生抽和少量泡粉丝的水"),
            step("蒸锅水开后上锅蒸五到六分钟", 360, "STEW"),
            step("出锅撒葱花，想更香可以再淋一点热油", 30, "STIR_FRY")
        )
    ),
    recipe(
        name = "剁椒鱼头",
        category = "荤菜",
        description = "家庭版重点是处理腥味和掌握蒸制时间",
        servings = 3,
        totalTime = 28,
        ingredients = ingredients(
            ingredient("鱼头", "1", "个"),
            ingredient("剁椒", "4", "勺"),
            ingredient("姜片", "4", "片"),
            ingredient("蒜末", "3", "瓣"),
            ingredient("蒸鱼豉油", "2", "勺")
        ),
        steps = steps(
            step("鱼头处理干净，从中间劈开但不要切断"),
            step("鱼头加料酒和姜片稍微抓匀，静置去腥十分钟", 600, "MARINATE"),
            step("盘底铺姜片，摆上鱼头，再铺蒜末和剁椒"),
            step("蒸锅水开后上锅蒸十二分钟左右", 720, "STEW"),
            step("取出后淋蒸鱼豉油，撒葱花"),
            step("最后泼一勺热油把香味激出来", 30, "STIR_FRY")
        )
    ),
    recipe(
        name = "口水鸡",
        category = "凉菜",
        description = "鸡肉嫩、红油香、料汁层次足，适合提前准备",
        servings = 3,
        totalTime = 35,
        ingredients = ingredients(
            ingredient("鸡腿", "3", "只"),
            ingredient("姜片", "4", "片"),
            ingredient("料酒", "1", "勺"),
            ingredient("蒜末", "3", "瓣"),
            ingredient("辣椒油", "2", "勺"),
            ingredient("生抽", "2", "勺"),
            ingredient("香醋", "1", "勺"),
            ingredient("花生碎", "适量")
        ),
        steps = steps(
            step("鸡腿冷水下锅，加姜片和料酒煮熟", 1200, "STEW"),
            step("煮好后立刻泡进凉开水里，让鸡皮更紧致"),
            step("鸡腿晾凉后拆块或斩块装盘"),
            step("调料汁：蒜末、辣椒油、生抽、香醋、少许白糖和鸡汤"),
            step("把料汁均匀淋在鸡肉上，静置五分钟入味", 300, "MARINATE"),
            step("最后撒花生碎和葱花再上桌")
        )
    ),
    recipe(
        name = "小炒黄牛肉",
        category = "荤菜",
        description = "火候要求高，但家庭灶也能做出很强的香气",
        servings = 2,
        totalTime = 18,
        ingredients = ingredients(
            ingredient("牛里脊", "250", "g"),
            ingredient("小米椒", "4", "个"),
            ingredient("芹菜", "2", "根"),
            ingredient("姜蒜末", "适量"),
            ingredient("生抽", "1", "勺"),
            ingredient("蚝油", "1", "勺")
        ),
        steps = steps(
            step("牛肉逆纹切薄片，加生抽和淀粉抓匀，腌五分钟", 300, "MARINATE"),
            step("小米椒切圈，芹菜切段，姜蒜剁末"),
            step("锅一定先烧热，再下油把牛肉迅速滑散至变色", 60, "STIR_FRY"),
            step("盛出牛肉，锅里补油爆香姜蒜和小米椒", 30, "STIR_FRY"),
            step("放芹菜快速翻炒到断生", 45, "STIR_FRY"),
            step("倒回牛肉，加蚝油翻匀，闻到浓郁香气立刻出锅", 30, "STIR_FRY")
        )
    ),
    recipe(
        name = "辣子鸡",
        category = "荤菜",
        description = "鸡块外香里嫩，辣椒主要提香不只是为了辣",
        servings = 3,
        totalTime = 30,
        ingredients = ingredients(
            ingredient("鸡腿肉", "500", "g"),
            ingredient("干辣椒", "12", "个"),
            ingredient("花椒", "1", "小勺"),
            ingredient("蒜末", "3", "瓣"),
            ingredient("生抽", "1", "勺"),
            ingredient("淀粉", "2", "勺")
        ),
        steps = steps(
            step("鸡腿肉切小块，加生抽和淀粉抓匀腌十分钟", 600, "MARINATE"),
            step("锅里烧油，把鸡块炸到外皮微黄后捞出", 240, "STIR_FRY"),
            step("油温升高后把鸡块复炸到更酥香", 90, "STIR_FRY"),
            step("锅里留少量底油，爆香干辣椒和花椒", 30, "STIR_FRY"),
            step("加入蒜末和鸡块快速翻匀", 45, "STIR_FRY"),
            step("最后撒熟白芝麻或葱花即可")
        )
    ),
    recipe(
        name = "香辣虾",
        category = "荤菜",
        description = "先煎后炒，虾壳香味会更足",
        servings = 2,
        totalTime = 22,
        ingredients = ingredients(
            ingredient("大虾", "500", "g"),
            ingredient("土豆", "1", "个"),
            ingredient("洋葱", "半", "个"),
            ingredient("干辣椒", "6", "个"),
            ingredient("花椒", "1", "小勺"),
            ingredient("火锅底料", "1", "小块")
        ),
        steps = steps(
            step("大虾剪须开背去线，土豆切条，洋葱切块"),
            step("土豆条先煎到边缘金黄后盛出", 240, "STIR_FRY"),
            step("锅里再把大虾煎到两面变红卷曲", 180, "STIR_FRY"),
            step("爆香干辣椒、花椒和少量火锅底料", 30, "STIR_FRY"),
            step("下洋葱、土豆条和大虾一起翻炒，让香味完全裹上去", 90, "STIR_FRY"),
            step("尝味后再决定是否补盐，通常底料本身已经够味")
        )
    ),
    recipe(
        name = "酸菜鱼",
        category = "荤菜",
        description = "家庭版重点是把鱼片处理嫩、汤底做顺口",
        servings = 3,
        totalTime = 35,
        ingredients = ingredients(
            ingredient("草鱼片", "500", "g"),
            ingredient("酸菜", "250", "g"),
            ingredient("姜片", "4", "片"),
            ingredient("蒜末", "3", "瓣"),
            ingredient("蛋清", "1", "个"),
            ingredient("淀粉", "1", "勺"),
            ingredient("白胡椒粉", "适量")
        ),
        steps = steps(
            step("鱼片加蛋清、少许盐和淀粉抓匀，先腌十分钟", 600, "MARINATE"),
            step("酸菜切段后用清水略冲一下，减少过重咸味"),
            step("锅里少油炒香姜蒜，下酸菜翻炒出香味", 120, "STIR_FRY"),
            step("加入热水煮开，先把酸菜汤底煮透", 600, "STEW"),
            step("把鱼片一片片下入锅中，变色就先别乱搅动", 120, "STEW"),
            step("等鱼片全部熟透后，撒白胡椒粉即可出锅")
        )
    ),
    recipe(
        name = "水煮肉片",
        category = "荤菜",
        description = "看着重口，其实家庭版也能做得香而不乱",
        servings = 3,
        totalTime = 30,
        ingredients = ingredients(
            ingredient("猪里脊", "300", "g"),
            ingredient("豆芽", "250", "g"),
            ingredient("生菜", "1", "小颗"),
            ingredient("郫县豆瓣酱", "1", "勺"),
            ingredient("干辣椒", "8", "个"),
            ingredient("花椒", "1", "小勺"),
            ingredient("淀粉", "1", "勺")
        ),
        steps = steps(
            step("里脊切片，加盐、料酒和淀粉抓匀腌制", 600, "MARINATE"),
            step("豆芽和生菜分别焯熟，铺在大碗底部", 90, "BLANCH"),
            step("锅里少油炒香豆瓣酱，加入热水煮成红汤", 240, "STEW"),
            step("把肉片一片片下锅，轻轻推动到全部变色", 120, "STEW"),
            step("连汤带肉倒进垫了蔬菜的大碗里"),
            step("另起锅炸香干辣椒和花椒，趁热浇在表面激香", 30, "STIR_FRY")
        )
    )
)

private fun soupRecipes(): List<DefaultRecipeSeed> = listOf(
    soupRecipe("紫菜蛋花汤", "非常快手，没时间时也能保证有热汤", 10, ingredients(ingredient("紫菜", "1", "小把"), ingredient("鸡蛋", "2", "个"), ingredient("虾皮", "1", "勺"), ingredient("葱花", "适量")), steps(step("锅里烧开两碗水，先放紫菜和虾皮煮出鲜味", 120, "STEW"), step("鸡蛋打散后转小火慢慢淋入，形成蛋花"), step("加盐调味，关火后撒葱花和几滴香油"))),
    soupRecipe("冬瓜丸子汤", "清爽不腻，适合夏天晚上喝", 20, ingredients(ingredient("冬瓜", "300", "g"), ingredient("猪肉末", "200", "g"), ingredient("姜末", "少许"), ingredient("鸡蛋", "1", "个")), steps(step("冬瓜去皮切片，肉末加姜末和蛋清顺一个方向搅上劲"), step("锅里烧开水，先把丸子挤下锅，定型后撇去浮沫", 180, "STEW"), step("放入冬瓜继续煮到透明发软", 480, "STEW"), step("加盐和白胡椒调味后出锅"))),
    soupRecipe("玉米排骨汤", "玉米香甜，汤底很温和", 60, ingredients(ingredient("排骨", "500", "g"), ingredient("玉米", "2", "段"), ingredient("胡萝卜", "1", "根"), ingredient("姜片", "3", "片")), steps(step("排骨焯水洗净，玉米切段，胡萝卜切块", 240, "BLANCH"), step("排骨加足量热水和姜片先炖四十分钟", 2400, "STEW"), step("放入玉米和胡萝卜继续炖到香味融合", 900, "STEW"), step("最后只需加盐，不要放太多重调料"))),
    soupRecipe("萝卜排骨汤", "萝卜清甜，适合秋冬常做", 60, ingredients(ingredient("排骨", "500", "g"), ingredient("白萝卜", "400", "g"), ingredient("姜片", "3", "片")), steps(step("排骨焯水后洗净，白萝卜切滚刀块", 240, "BLANCH"), step("排骨和姜片先炖四十分钟", 2400, "STEW"), step("加入白萝卜继续炖到变软透亮", 900, "STEW"), step("出锅前加盐即可，汤味会很干净"))),
    soupRecipe("海带豆腐汤", "鲜而不腻，做法也足够简单", 15, ingredients(ingredient("海带结", "150", "g"), ingredient("嫩豆腐", "1", "盒"), ingredient("虾皮", "1", "勺"), ingredient("葱花", "适量")), steps(step("海带洗净，豆腐切块，虾皮冲一下备用"), step("锅里烧开水，先下海带和虾皮煮出鲜味", 300, "STEW"), step("放入豆腐小火煮几分钟，避免把豆腐搅碎", 240, "STEW"), step("最后加盐和葱花即可"))),
    soupRecipe("菌菇豆腐汤", "鲜味全部来自食材本身，很适合清淡晚餐", 15, ingredients(ingredient("白玉菇", "150", "g"), ingredient("蟹味菇", "150", "g"), ingredient("嫩豆腐", "1", "盒"), ingredient("葱花", "适量")), steps(step("菌菇切根洗净，豆腐切块"), step("锅里少油把菌菇先炒出水汽", 120, "STIR_FRY"), step("加入热水煮开，再放豆腐小火煮五分钟", 300, "STEW"), step("最后只需加盐和白胡椒调味"))),
    soupRecipe("鲫鱼豆腐汤", "奶白汤色的关键是先把鱼煎透", 35, ingredients(ingredient("鲫鱼", "1", "条"), ingredient("豆腐", "1", "块"), ingredient("姜片", "4", "片"), ingredient("葱段", "适量")), steps(step("鲫鱼处理干净并擦干表面水分"), step("锅热后下油，把鲫鱼煎到两面金黄", 300, "STIR_FRY"), step("加入姜片和足量开水，大火先煮出白汤", 600, "STEW"), step("放入豆腐后转中小火再煮十分钟", 600, "STEW"), step("最后加盐和葱段即可"))),
    soupRecipe("丝瓜蛋汤", "汤色清亮，丝瓜甜味很明显", 12, ingredients(ingredient("丝瓜", "1", "根"), ingredient("鸡蛋", "2", "个"), ingredient("姜丝", "少许")), steps(step("丝瓜去皮切滚刀块，鸡蛋打散"), step("锅里少油下姜丝和丝瓜略炒", 60, "STIR_FRY"), step("加入两碗热水煮到丝瓜变软", 240, "STEW"), step("转小火淋蛋液，加盐调味"))),
    soupRecipe("菠菜猪肝汤", "猪肝嫩的关键是切薄片和别久煮", 15, ingredients(ingredient("猪肝", "200", "g"), ingredient("菠菜", "200", "g"), ingredient("姜丝", "适量"), ingredient("料酒", "1", "勺")), steps(step("猪肝切薄片，清水泡洗后加料酒和淀粉抓匀"), step("菠菜焯水后切段", 30, "BLANCH"), step("锅里烧开水，下姜丝后把猪肝滑入锅中", 90, "STEW"), step("猪肝刚变色就放菠菜和盐，立刻关火"))),
    soupRecipe("白菜豆腐汤", "朴素但很舒服的一碗家常热汤", 12, ingredients(ingredient("白菜", "250", "g"), ingredient("嫩豆腐", "1", "盒"), ingredient("虾皮", "1", "勺")), steps(step("白菜切段，豆腐切块"), step("锅里先把白菜煮软", 240, "STEW"), step("加入豆腐和虾皮再煮几分钟", 240, "STEW"), step("最后加盐和白胡椒即可"))),
    soupRecipe("三鲜汤", "一锅里同时吃到丸子、菌菇和蔬菜", 20, ingredients(ingredient("肉丸", "10", "个"), ingredient("鲜香菇", "6", "朵"), ingredient("青菜", "200", "g"), ingredient("姜片", "2", "片")), steps(step("香菇切片，青菜洗净"), step("锅里烧开水，先下肉丸和姜片煮透", 360, "STEW"), step("放香菇煮出鲜味", 180, "STEW"), step("最后下青菜，断生后加盐出锅"))),
    soupRecipe("冬瓜虾皮汤", "非常清爽，热天喝也不压胃", 10, ingredients(ingredient("冬瓜", "300", "g"), ingredient("虾皮", "1", "勺"), ingredient("葱花", "适量")), steps(step("冬瓜切薄片"), step("锅里少油把虾皮炒一下，再加水煮开", 60, "STIR_FRY"), step("放入冬瓜煮到透明发软", 240, "STEW"), step("加盐后撒葱花即可"))),
    soupRecipe("山药排骨汤", "山药绵软，适合作为补充体力的家常汤", 65, ingredients(ingredient("排骨", "500", "g"), ingredient("山药", "300", "g"), ingredient("姜片", "3", "片")), steps(step("排骨焯水，山药去皮切段", 240, "BLANCH"), step("排骨先炖四十五分钟", 2700, "STEW"), step("加入山药继续炖到软糯", 900, "STEW"), step("最后加盐即可"))),
    soupRecipe("莲藕排骨汤", "藕香很足，越炖越甜", 70, ingredients(ingredient("排骨", "500", "g"), ingredient("莲藕", "1", "节"), ingredient("姜片", "3", "片")), steps(step("排骨焯水，莲藕切块", 240, "BLANCH"), step("排骨和姜片加热水慢炖五十分钟", 3000, "STEW"), step("下莲藕继续炖二十分钟左右", 1200, "STEW"), step("加盐后再煮两分钟让味道融合"))),
    soupRecipe("罗宋汤", "家庭版也能做出酸甜浓郁的层次", 35, ingredients(ingredient("牛肉", "250", "g"), ingredient("番茄", "3", "个"), ingredient("卷心菜", "200", "g"), ingredient("土豆", "1", "个"), ingredient("洋葱", "1", "个")), steps(step("牛肉切小块焯水，番茄切块，其余蔬菜切丁", 240, "BLANCH"), step("锅里先炒洋葱和番茄到明显出汁", 240, "STIR_FRY"), step("加入牛肉和其他蔬菜翻匀后加热水", 60, "STEW"), step("小火煮到土豆软烂、汤底融合", 1200, "STEW"), step("根据口味补少许盐和黑胡椒"))),
    soupRecipe("萝卜牛腩汤", "汤清肉香，很适合当天现炖现喝", 80, ingredients(ingredient("牛腩", "700", "g"), ingredient("白萝卜", "400", "g"), ingredient("姜片", "4", "片")), steps(step("牛腩焯水洗净，白萝卜切块", 240, "BLANCH"), step("牛腩加热水和姜片慢炖一小时", 3600, "STEW"), step("放白萝卜继续炖到软透", 900, "STEW"), step("最后加盐，别放太多调料抢汤味"))),
    soupRecipe("紫菜虾皮汤", "比蛋花汤更快，真正十分钟内能搞定", 8, ingredients(ingredient("紫菜", "1", "小把"), ingredient("虾皮", "1", "勺"), ingredient("香油", "几滴")), steps(step("锅里烧开水，下紫菜和虾皮"), step("煮两分钟后加盐和香油"), step("喜欢的话最后撒葱花再盛出"))),
    soupRecipe("玉米胡萝卜排骨汤", "甜味明显，孩子通常更喜欢这一锅", 65, ingredients(ingredient("排骨", "500", "g"), ingredient("玉米", "2", "段"), ingredient("胡萝卜", "1", "根"), ingredient("姜片", "3", "片")), steps(step("排骨焯水，玉米切段，胡萝卜切块", 240, "BLANCH"), step("排骨先炖四十五分钟", 2700, "STEW"), step("放玉米和胡萝卜继续炖二十分钟", 1200, "STEW"), step("最后加盐即可"))),
    soupRecipe("雪梨银耳汤", "润口舒服，可以当饭后甜汤", 40, ingredients(ingredient("雪梨", "1", "个"), ingredient("银耳", "半", "朵"), ingredient("冰糖", "适量"), ingredient("红枣", "4", "颗")), steps(step("银耳泡发后去根撕小朵，雪梨去皮切块"), step("银耳先煮到开始出胶", 1500, "STEW"), step("加入雪梨、红枣和冰糖继续煮软", 900, "STEW"), step("汤感顺滑后关火，热喝冷喝都可以"))),
    soupRecipe("红枣银耳汤", "胶质感比雪梨版更明显，适合提前煮好", 45, ingredients(ingredient("银耳", "半", "朵"), ingredient("红枣", "8", "颗"), ingredient("枸杞", "1", "小把"), ingredient("冰糖", "适量")), steps(step("银耳泡发撕小朵，红枣去核更容易出味"), step("银耳先煮二十五分钟到微微出胶", 1500, "STEW"), step("加入红枣和冰糖继续煮", 900, "STEW"), step("最后五分钟放枸杞，避免久煮发酸", 300, "STEW"))),
    soupRecipe("绿豆百合汤", "夏天常喝的一锅甜汤，冰过以后也很好", 40, ingredients(ingredient("绿豆", "80", "g"), ingredient("干百合", "20", "g"), ingredient("冰糖", "适量")), steps(step("绿豆提前泡半小时更容易煮开花", 1800, "MARINATE"), step("锅里加足量水，先煮绿豆到微微开花", 1500, "STEW"), step("加入百合和冰糖继续煮到豆子绵软", 600, "STEW"), step("热饮清爽，冷藏后也适合夏天"))),
    soupRecipe("南瓜小米粥", "当早餐很稳，口感软糯顺滑", 35, ingredients(ingredient("南瓜", "250", "g"), ingredient("小米", "80", "g"), ingredient("清水", "适量")), steps(step("南瓜切小块，小米淘洗干净"), step("锅里加水煮开后下小米和南瓜", 60, "STEW"), step("转小火慢慢煮到南瓜化开、小米软糯", 1800, "STEW"), step("中途记得偶尔搅拌一下防止糊底")))
)

private fun stapleRecipes(): List<DefaultRecipeSeed> = listOf(
    stapleRecipe("扬州炒饭", "主食", "配料丰富，属于一锅解决主食和配菜的类型", 2, 18, ingredients(ingredient("米饭", "2", "碗"), ingredient("鸡蛋", "2", "个"), ingredient("火腿", "80", "g"), ingredient("玉米粒", "60", "g"), ingredient("豌豆", "60", "g"), ingredient("胡萝卜丁", "半", "根")), steps(step("米饭提前打散，鸡蛋打匀，配料全部切成小丁"), step("先把鸡蛋炒成小块盛出", 60, "STIR_FRY"), step("锅里少油炒香火腿丁和胡萝卜丁", 90, "STIR_FRY"), step("放玉米粒和豌豆继续翻炒", 60, "STIR_FRY"), step("加入米饭大火炒散，再倒回鸡蛋", 120, "STIR_FRY"), step("加盐调味，炒到颗粒分明即可"))),
    stapleRecipe("酱油炒饭", "主食", "调味简单但特别有烟火气", 2, 12, ingredients(ingredient("米饭", "2", "碗"), ingredient("鸡蛋", "2", "个"), ingredient("葱花", "适量"), ingredient("生抽", "1", "勺"), ingredient("老抽", "半", "勺")), steps(step("米饭提前打散，鸡蛋打匀"), step("先炒鸡蛋盛出", 60, "STIR_FRY"), step("锅里下少许油和葱花炒香", 20, "STIR_FRY"), step("加入米饭快速炒散，再淋入生抽和老抽", 120, "STIR_FRY"), step("倒回鸡蛋翻匀后立刻出锅"))),
    stapleRecipe("腊肉炒饭", "主食", "腊肉本身就很香，少调味更合适", 2, 15, ingredients(ingredient("米饭", "2", "碗"), ingredient("腊肉", "120", "g"), ingredient("青蒜", "2", "根"), ingredient("鸡蛋", "2", "个")), steps(step("腊肉煮软后切丁，青蒜切碎，鸡蛋打匀", 600, "STEW"), step("先把鸡蛋炒散盛出", 60, "STIR_FRY"), step("锅里煸炒腊肉出香味和部分油脂", 120, "STIR_FRY"), step("加入米饭炒散，再放鸡蛋和青蒜", 120, "STIR_FRY"), step("尝味后再决定要不要补一点盐"))),
    stapleRecipe("番茄鸡蛋面", "主食", "酸甜带汤，晚饭不想太复杂时最合适", 2, 15, ingredients(ingredient("挂面", "200", "g"), ingredient("番茄", "2", "个"), ingredient("鸡蛋", "2", "个"), ingredient("葱花", "适量")), steps(step("番茄切块，鸡蛋打散"), step("先炒鸡蛋盛出", 60, "STIR_FRY"), step("把番茄炒到出汁，加水煮开成汤底", 240, "STEW"), step("下面条煮到八九成熟", 240, "STEW"), step("倒回鸡蛋，尝味后关火"))),
    stapleRecipe("葱油拌面", "主食", "葱香是核心，葱一定要熬到位", 2, 15, ingredients(ingredient("面条", "200", "g"), ingredient("小葱", "1", "大把"), ingredient("生抽", "3", "勺"), ingredient("白糖", "1", "勺")), steps(step("小葱切长段，生抽和白糖先调匀"), step("锅里多放一点油，把葱段小火慢慢炸到焦黄", 300, "STIR_FRY"), step("把调好的酱汁倒入锅里煮十几秒", 20, "STIR_FRY"), step("面条煮熟后沥干，趁热拌入葱油汁"))),
    stapleRecipe("炸酱面", "主食", "提前把酱炒好，后面就会特别从容", 3, 25, ingredients(ingredient("面条", "300", "g"), ingredient("猪肉末", "200", "g"), ingredient("甜面酱", "2", "勺"), ingredient("黄豆酱", "2", "勺"), ingredient("黄瓜", "1", "根")), steps(step("黄瓜切丝，甜面酱和黄豆酱提前调匀"), step("锅里下油把肉末炒散炒香", 180, "STIR_FRY"), step("放酱料小火慢炒出香味", 180, "STIR_FRY"), step("加少量清水把酱熬顺，别太干", 180, "STEW"), step("面条煮熟后配黄瓜丝和炸酱拌匀"))),
    stapleRecipe("阳春面", "主食", "清简单纯，越是基础越讲究汤头平衡", 1, 10, ingredients(ingredient("面条", "120", "g"), ingredient("葱花", "适量"), ingredient("生抽", "1", "勺"), ingredient("猪油", "半", "勺"), ingredient("白胡椒", "少许")), steps(step("碗里先放生抽、猪油、葱花和白胡椒"), step("面条煮熟的同时舀入一大勺面汤到碗里"), step("把面条捞进碗中拌匀即可"))),
    stapleRecipe("酸汤面", "主食", "酸辣开胃，适合没胃口的时候", 2, 12, ingredients(ingredient("面条", "200", "g"), ingredient("蒜末", "2", "瓣"), ingredient("辣椒面", "1", "勺"), ingredient("香醋", "2", "勺"), ingredient("生抽", "1", "勺")), steps(step("大碗里放蒜末、辣椒面、生抽和香醋"), step("煮面同时浇一勺热面汤到碗里激香"), step("面条煮熟后捞入碗中，按口味加盐和葱花"))),
    stapleRecipe("牛肉炒河粉", "主食", "大火快炒才能带出镬气和香味", 2, 18, ingredients(ingredient("河粉", "400", "g"), ingredient("牛肉", "200", "g"), ingredient("豆芽", "150", "g"), ingredient("韭黄", "100", "g"), ingredient("生抽", "2", "勺")), steps(step("牛肉切片加生抽和淀粉抓匀，河粉先抖散"), step("先炒牛肉到变色盛出", 60, "STIR_FRY"), step("爆香锅后快速翻炒豆芽和韭黄", 45, "STIR_FRY"), step("下河粉和牛肉，沿锅边淋生抽大火翻匀", 90, "STIR_FRY"), step("闻到明显锅气后立刻出锅"))),
    stapleRecipe("家常炒面", "主食", "面条带点焦香边最好吃", 2, 18, ingredients(ingredient("鲜面条", "250", "g"), ingredient("卷心菜", "150", "g"), ingredient("胡萝卜", "半", "根"), ingredient("鸡蛋", "2", "个"), ingredient("生抽", "1", "勺")), steps(step("面条煮到八成熟后过凉水沥干", 180, "STEW"), step("先把鸡蛋炒散盛出", 60, "STIR_FRY"), step("卷心菜和胡萝卜先炒软", 120, "STIR_FRY"), step("放面条和鸡蛋，加生抽大火翻炒", 120, "STIR_FRY"), step("看到面条有一点点焦边就最好"))),
    stapleRecipe("鸡丝凉面", "主食", "夏天特别实用，提前做好也不坨", 2, 20, ingredients(ingredient("面条", "200", "g"), ingredient("鸡胸肉", "150", "g"), ingredient("黄瓜", "1", "根"), ingredient("花生酱", "1", "勺"), ingredient("生抽", "1", "勺"), ingredient("香醋", "1", "勺")), steps(step("鸡胸肉煮熟后撕丝，黄瓜切丝", 600, "STEW"), step("面条煮熟后过凉开水，拌少许油防粘"), step("花生酱、生抽、香醋和一点点糖调成拌汁"), step("面条配鸡丝和黄瓜丝，吃前再拌匀"))),
    stapleRecipe("土豆焖饭", "主食", "一锅饭里自带菜香，很适合忙碌工作日", 3, 30, ingredients(ingredient("大米", "2", "杯"), ingredient("土豆", "1", "个"), ingredient("胡萝卜", "半", "根"), ingredient("腊肠", "1", "根"), ingredient("生抽", "1", "勺")), steps(step("大米淘洗好，土豆胡萝卜切丁，腊肠切片"), step("锅里稍微把土豆和腊肠炒香", 120, "STIR_FRY"), step("把食材倒进电饭煲，加米和正常煮饭水量"), step("淋入生抽拌匀后按煮饭键", 1800, "STEW"), step("焖好后再翻松，避免底部结块"))),
    stapleRecipe("腊肠焖饭", "主食", "腊肠的油香会自然渗进米饭里", 3, 28, ingredients(ingredient("大米", "2", "杯"), ingredient("腊肠", "2", "根"), ingredient("香菇", "4", "朵"), ingredient("青豆", "60", "g"), ingredient("生抽", "1", "勺")), steps(step("腊肠切片，香菇切丁，大米淘洗干净"), step("食材全部放进电饭煲，加好煮饭水"), step("加生抽稍微拌匀，按下煮饭键", 1800, "STEW"), step("饭熟后焖五分钟再打开，香味会更完整", 300, "STEW"))),
    stapleRecipe("排骨焖饭", "主食", "米饭吸了排骨汤汁后特别香", 3, 45, ingredients(ingredient("大米", "2", "杯"), ingredient("排骨", "350", "g"), ingredient("土豆", "1", "个"), ingredient("胡萝卜", "半", "根"), ingredient("生抽", "2", "勺")), steps(step("排骨焯水后炒到表面收紧，再用生抽上色", 360, "STIR_FRY"), step("土豆胡萝卜切块，大米淘洗好"), step("把排骨和蔬菜铺在米上，加正常煮饭水量", 60, "STEW"), step("按煮饭键，熟后焖十分钟再拌匀", 2100, "STEW"))),
    stapleRecipe("香菇鸡肉焖饭", "主食", "鸡肉和香菇组合基本不会出错", 3, 35, ingredients(ingredient("大米", "2", "杯"), ingredient("鸡腿肉", "250", "g"), ingredient("香菇", "6", "朵"), ingredient("胡萝卜", "半", "根"), ingredient("生抽", "1", "勺")), steps(step("鸡腿肉切丁，香菇和胡萝卜切丁"), step("先把鸡肉和香菇炒出香味", 180, "STIR_FRY"), step("食材和大米一起放入电饭煲，加入正常煮饭水量"), step("加生抽拌匀后煮饭", 1800, "STEW"), step("出锅前翻松，确保底部米饭也混匀"))),
    stapleRecipe("青菜鸡蛋粥", "主食", "早晚都能吃，胃口不佳时尤其合适", 2, 30, ingredients(ingredient("大米", "80", "g"), ingredient("鸡蛋", "1", "个"), ingredient("青菜", "150", "g")), steps(step("大米淘洗后加足量水熬成粥底", 1500, "STEW"), step("青菜切碎，鸡蛋打散"), step("先放青菜煮软，再淋鸡蛋液", 120, "STEW"), step("加盐后再滚半分钟即可"))),
    stapleRecipe("皮蛋瘦肉粥", "主食", "顺滑咸香，经典早餐型选手", 2, 35, ingredients(ingredient("大米", "80", "g"), ingredient("瘦肉", "120", "g"), ingredient("皮蛋", "2", "个"), ingredient("姜丝", "少许")), steps(step("大米煮成粥底，瘦肉切丝加盐和淀粉抓匀"), step("皮蛋切小块"), step("先下肉丝煮到变色", 120, "STEW"), step("再放皮蛋和姜丝煮五分钟", 300, "STEW"), step("最后按口味补盐和白胡椒"))),
    stapleRecipe("小米红枣粥", "主食", "偏温和甜口，早餐很好用", 2, 35, ingredients(ingredient("小米", "80", "g"), ingredient("红枣", "8", "颗"), ingredient("清水", "适量")), steps(step("小米淘洗，红枣去核更容易出味"), step("锅里加水烧开后下小米和红枣", 60, "STEW"), step("转小火慢熬到小米开花", 1800, "STEW"), step("喜欢甜一点可在最后加少量冰糖"))),
    stapleRecipe("白菜肉丝年糕", "主食", "年糕吸汁后特别软糯，顶饱感很强", 2, 18, ingredients(ingredient("年糕", "300", "g"), ingredient("猪里脊", "150", "g"), ingredient("白菜", "200", "g"), ingredient("生抽", "1", "勺")), steps(step("肉丝先加生抽和淀粉腌五分钟", 300, "MARINATE"), step("白菜切段，年糕片分散开"), step("先炒肉丝盛出，再炒白菜到出水", 180, "STIR_FRY"), step("加入年糕和少量热水焖软", 240, "STEW"), step("倒回肉丝翻匀后即可"))),
    stapleRecipe("葱油饼", "小吃", "面团别太硬，层次会更明显", 3, 35, ingredients(ingredient("面粉", "300", "g"), ingredient("热水", "170", "ml"), ingredient("葱花", "1", "大把"), ingredient("盐", "适量")), steps(step("面粉加热水揉成软面团，醒二十分钟", 1200, "MARINATE"), step("面团擀开后抹油撒盐和葱花，卷起再盘起"), step("再次擀成饼胚"), step("平底锅刷油，中小火把两面烙到金黄起层", 480, "STIR_FRY"))),
    stapleRecipe("韭菜盒子", "小吃", "馅料先把水分控住，烙的时候更好操作", 3, 40, ingredients(ingredient("面粉", "300", "g"), ingredient("韭菜", "250", "g"), ingredient("鸡蛋", "3", "个"), ingredient("粉丝", "1", "小把")), steps(step("面粉加温水揉面并醒二十分钟", 1200, "MARINATE"), step("粉丝泡软切碎，鸡蛋炒散放凉，韭菜切末"), step("把馅料拌匀时最后再加盐，避免提前出水"), step("包成盒子形状，锅里少油两面烙熟", 480, "STIR_FRY"))),
    stapleRecipe("鸡蛋饼", "小吃", "早餐最常见，但细节做对会更松软", 2, 12, ingredients(ingredient("面粉", "120", "g"), ingredient("鸡蛋", "2", "个"), ingredient("葱花", "适量"), ingredient("清水", "适量")), steps(step("面粉、鸡蛋和清水调成能流动的面糊"), step("加入葱花和少许盐拌匀"), step("平底锅刷油，倒一勺面糊转匀成薄饼", 90, "STIR_FRY"), step("两面都凝固微黄后即可出锅"))),
    stapleRecipe("手抓饼卷蛋", "小吃", "家里有半成品时非常高效", 1, 10, ingredients(ingredient("手抓饼", "1", "张"), ingredient("鸡蛋", "1", "个"), ingredient("生菜", "2", "片"), ingredient("火腿", "1", "根")), steps(step("平底锅放手抓饼，小火煎到开始起酥层", 120, "STIR_FRY"), step("打一个鸡蛋在饼上抹匀并翻面煎熟", 60, "STIR_FRY"), step("加生菜和火腿卷起来，切半就能吃")))
)

private fun coldDishRecipes(): List<DefaultRecipeSeed> = listOf(
    recipe("拍黄瓜", "凉菜", "蒜香和醋香都很直接，夏天最实用", 2, 8, ingredients(ingredient("黄瓜", "2", "根"), ingredient("蒜末", "3", "瓣"), ingredient("香醋", "1", "勺"), ingredient("生抽", "1", "勺"), ingredient("辣椒油", "1", "勺")), steps(step("黄瓜拍裂后切段，撒少许盐抓匀静置五分钟", 300, "MARINATE"), step("倒掉多余水分，加入蒜末、香醋、生抽和辣椒油"), step("拌匀后冷藏十分钟更入味", 600, "MARINATE"))),
    recipe("凉拌木耳", "凉菜", "脆爽又解腻，是很好的搭配菜", 2, 12, ingredients(ingredient("泡发木耳", "200", "g"), ingredient("蒜末", "3", "瓣"), ingredient("小米椒", "2", "个"), ingredient("香醋", "1", "勺"), ingredient("生抽", "1", "勺")), steps(step("木耳焯熟后立刻过凉水，沥干备用", 120, "BLANCH"), step("蒜末、小米椒、生抽、香醋和少许糖调成料汁"), step("把料汁拌进木耳里，静置五分钟再上桌", 300, "MARINATE"))),
    recipe("凉拌腐竹", "凉菜", "腐竹提前泡透以后特别吸味", 2, 15, ingredients(ingredient("泡发腐竹", "220", "g"), ingredient("黄瓜", "1", "根"), ingredient("蒜末", "2", "瓣"), ingredient("辣椒油", "1", "勺")), steps(step("腐竹泡软后切段，焯一分钟捞出过凉", 60, "BLANCH"), step("黄瓜拍碎切段"), step("加入蒜末、辣椒油、生抽和香醋拌匀", 60, "STIR_FRY"), step("静置十分钟再吃更入味", 600, "MARINATE"))),
    recipe("凉拌海带丝", "凉菜", "海带处理干净后口感会非常清爽", 2, 12, ingredients(ingredient("海带丝", "250", "g"), ingredient("蒜末", "2", "瓣"), ingredient("香醋", "1", "勺"), ingredient("生抽", "1", "勺"), ingredient("辣椒油", "1", "勺")), steps(step("海带丝焯水后过凉，沥干备用", 120, "BLANCH"), step("调入蒜末、香醋、生抽和辣椒油"), step("拌匀后静置五分钟，让海带把味道吸进去", 300, "MARINATE"))),
    recipe("凉拌土豆丝", "凉菜", "酸辣脆爽，和热菜搭着吃特别舒服", 2, 12, ingredients(ingredient("土豆", "2", "个"), ingredient("胡萝卜", "半", "根"), ingredient("蒜末", "2", "瓣"), ingredient("香醋", "1", "勺")), steps(step("土豆和胡萝卜切细丝，土豆丝多洗几遍淀粉"), step("开水里焯到刚断生，马上过凉", 45, "BLANCH"), step("加蒜末、香醋、生抽和少许辣椒油拌匀"))),
    recipe("老醋花生", "凉菜", "花生要保持脆，料汁别太早拌", 2, 10, ingredients(ingredient("炸花生米", "120", "g"), ingredient("洋葱", "半", "个"), ingredient("青椒", "半", "个"), ingredient("香醋", "2", "勺"), ingredient("白糖", "1", "勺")), steps(step("洋葱和青椒切小丁"), step("香醋、白糖和少许生抽调成酸甜汁"), step("吃前再把花生和蔬菜拌匀，避免花生回软"))),
    recipe("皮蛋豆腐", "凉菜", "越简单越考验料汁平衡", 2, 8, ingredients(ingredient("内酯豆腐", "1", "盒"), ingredient("皮蛋", "2", "个"), ingredient("蒜末", "2", "瓣"), ingredient("生抽", "1", "勺"), ingredient("香油", "几滴")), steps(step("豆腐倒扣到盘里，轻轻切块"), step("皮蛋切瓣摆在豆腐周围"), step("蒜末、生抽、香油和少量醋调匀后淋上即可"))),
    recipe("凉拌莴笋丝", "凉菜", "口感很脆，夏天特别受欢迎", 2, 10, ingredients(ingredient("莴笋", "1", "根"), ingredient("蒜末", "2", "瓣"), ingredient("香醋", "1", "勺"), ingredient("辣椒油", "1", "勺")), steps(step("莴笋去皮切丝，撒少许盐抓匀腌五分钟", 300, "MARINATE"), step("把腌出的水挤掉"), step("拌入蒜末、香醋和辣椒油即可"))),
    recipe("凉拌西红柿", "凉菜", "最简单的一道凉菜，但很能解腻", 2, 5, ingredients(ingredient("番茄", "2", "个"), ingredient("白糖", "适量")), steps(step("番茄切厚片或块状装盘"), step("表面撒适量白糖，静置几分钟让番茄出汁", 300, "MARINATE"))),
    recipe("凉拌豆皮", "凉菜", "豆皮有咬劲，做冷盘也很合适", 2, 12, ingredients(ingredient("豆皮", "2", "张"), ingredient("黄瓜", "1", "根"), ingredient("蒜末", "2", "瓣"), ingredient("生抽", "1", "勺")), steps(step("豆皮切丝后焯水四十秒去豆腥味", 40, "BLANCH"), step("黄瓜切丝"), step("调好蒜末、生抽、香醋和香油拌匀"))),
    recipe("凉拌金针菇", "凉菜", "清爽带脆，配烤物和油炸都很好", 2, 10, ingredients(ingredient("金针菇", "250", "g"), ingredient("蒜末", "2", "瓣"), ingredient("小米椒", "2", "个"), ingredient("生抽", "1", "勺")), steps(step("金针菇切根洗净后焯熟", 90, "BLANCH"), step("过凉水后挤干多余水分"), step("拌入蒜末、小米椒、生抽和少许醋"))),
    recipe("凉拌芹菜花生", "凉菜", "脆嫩花生和芹菜很搭，适合提前准备", 2, 14, ingredients(ingredient("芹菜", "250", "g"), ingredient("煮花生", "120", "g"), ingredient("胡萝卜", "半", "根"), ingredient("盐", "适量"), ingredient("香油", "几滴")), steps(step("芹菜和胡萝卜切段后焯到断生", 60, "BLANCH"), step("和煮熟的花生放在一起"), step("加盐、香油和少许白胡椒拌匀"))),
    recipe("柠檬手撕鸡", "凉菜", "酸香清爽，夏季做一盒放冰箱很实用", 3, 25, ingredients(ingredient("鸡胸肉", "2", "块"), ingredient("柠檬", "1", "个"), ingredient("洋葱", "半", "个"), ingredient("香菜", "1", "小把"), ingredient("生抽", "2", "勺")), steps(step("鸡胸肉加姜片煮熟后放凉撕丝", 900, "STEW"), step("洋葱切丝泡冷水去辛味，柠檬去籽切片"), step("调生抽、香醋、少许蜂蜜和辣椒油"), step("把鸡丝、洋葱和柠檬片拌匀，冷藏十分钟更好吃", 600, "MARINATE"))),
    recipe("凉拌鸡丝粉皮", "凉菜", "鸡丝和粉皮吸满料汁，夏天特别省事", 2, 18, ingredients(ingredient("鸡胸肉", "1", "块"), ingredient("粉皮", "2", "张"), ingredient("黄瓜", "1", "根"), ingredient("蒜末", "2", "瓣"), ingredient("生抽", "1", "勺")), steps(step("鸡胸肉煮熟放凉后撕丝", 720, "STEW"), step("粉皮切宽条后焯一下再过凉", 30, "BLANCH"), step("黄瓜切丝"), step("蒜末、生抽、香醋和一点辣椒油调成料汁"), step("把鸡丝、粉皮和黄瓜丝拌匀即可"))),
    recipe("凉拌茄子", "凉菜", "茄子蒸熟后少油也能很香很入味", 2, 16, ingredients(ingredient("长茄子", "2", "根"), ingredient("蒜末", "3", "瓣"), ingredient("生抽", "1", "勺"), ingredient("香醋", "1", "勺"), ingredient("香油", "几滴")), steps(step("茄子切长条，上锅蒸到软透", 600, "STEW"), step("蒸好后放凉并轻轻挤掉多余水分"), step("蒜末、生抽、香醋和香油调匀"), step("把料汁拌进茄子里，静置几分钟再上桌", 300, "MARINATE"))),
    recipe("凉拌白菜心", "凉菜", "非常清口，适合作为重口主菜的搭配菜", 2, 10, ingredients(ingredient("白菜心", "250", "g"), ingredient("蒜末", "2", "瓣"), ingredient("香醋", "1", "勺"), ingredient("白糖", "半", "勺")), steps(step("白菜心切细丝，用凉开水冲洗后沥干"), step("蒜末、香醋和白糖调成清爽料汁"), step("拌匀后静置五分钟即可", 300, "MARINATE"))),
    recipe("凉拌萝卜丝", "凉菜", "脆爽又解腻，适合当日常小配菜", 2, 10, ingredients(ingredient("白萝卜", "1", "段"), ingredient("香醋", "1", "勺"), ingredient("白糖", "1", "小勺"), ingredient("辣椒油", "少许")), steps(step("白萝卜切细丝，加少许盐抓匀腌出一点水", 300, "MARINATE"), step("轻轻挤掉水分"), step("加入香醋、白糖和少量辣椒油拌匀"))),
    recipe("凉拌豆芽", "凉菜", "焯到刚熟最重要，脆口感会更明显", 2, 8, ingredients(ingredient("绿豆芽", "250", "g"), ingredient("蒜末", "2", "瓣"), ingredient("生抽", "1", "勺"), ingredient("香醋", "1", "勺")), steps(step("豆芽摘净后焯到刚断生", 30, "BLANCH"), step("过凉后沥干"), step("加蒜末、生抽、香醋和少许香油拌匀")))
)

private fun snackAndDessertRecipes(): List<DefaultRecipeSeed> = listOf(
    recipe("茶叶蛋", "小吃", "提前浸泡是入味关键，做一锅能吃两三天", 6, 45, ingredients(ingredient("鸡蛋", "8", "个"), ingredient("红茶包", "2", "个"), ingredient("生抽", "3", "勺"), ingredient("八角", "2", "个")), steps(step("鸡蛋冷水下锅煮熟后轻轻敲裂蛋壳", 600, "STEW"), step("另起锅加水、茶包、生抽和八角煮开", 300, "STEW"), step("放入鸡蛋小火煮十分钟", 600, "STEW"), step("关火后至少浸泡两小时再吃更入味", 7200, "MARINATE"))),
    recipe("烤红薯", "小吃", "家庭烤箱就能做，选细长一点更容易烤透", 2, 45, ingredients(ingredient("红薯", "2", "个")), steps(step("红薯刷洗干净并擦干水分"), step("烤箱预热到200度"), step("红薯放中层烤二十五分钟后翻面继续烤", 2700, "STEW"), step("竹签能轻松扎透就是熟了"))),
    recipe("烤玉米", "小吃", "刷一点调味油就很香，适合家庭简易版", 2, 25, ingredients(ingredient("甜玉米", "2", "根"), ingredient("食用油", "1", "勺"), ingredient("椒盐", "适量")), steps(step("玉米煮到七八成熟后捞出沥干", 600, "STEW"), step("表面刷油，撒少量椒盐"), step("放进烤箱或空气炸锅烤到表面微焦", 900, "STEW"))),
    recipe("炸馒头片", "小吃", "外酥里软，早餐和夜宵都很实用", 2, 10, ingredients(ingredient("馒头", "2", "个"), ingredient("鸡蛋", "1", "个"), ingredient("盐", "少许")), steps(step("馒头切厚片，鸡蛋打散加一小撮盐"), step("馒头片两面蘸蛋液"), step("平底锅放少量油，把两面煎到金黄酥香", 240, "STIR_FRY"))),
    recipe("红糖糍粑", "甜品", "家庭版不必油炸太深，表面焦香就够", 2, 15, ingredients(ingredient("糍粑", "6", "块"), ingredient("红糖", "2", "勺"), ingredient("黄豆粉", "适量")), steps(step("锅里少油把糍粑煎到两面鼓起发软", 300, "STIR_FRY"), step("另取小锅把红糖和少量水熬成糖浆", 120, "STIR_FRY"), step("把糖浆淋在糍粑上，再撒黄豆粉"))),
    recipe("冰糖炖雪梨", "甜品", "做法朴素，但很适合天气干燥时来一盅", 2, 35, ingredients(ingredient("雪梨", "2", "个"), ingredient("冰糖", "适量"), ingredient("枸杞", "1", "小把")), steps(step("雪梨去皮切块"), step("和冰糖一起放进炖盅或小锅里，加少量清水"), step("小火炖到雪梨发软、汤汁清甜", 1800, "STEW"), step("最后五分钟放枸杞即可", 300, "STEW"))),
    recipe("酒酿圆子", "甜品", "甜香温和，夜里当热甜汤也很舒服", 2, 15, ingredients(ingredient("酒酿", "200", "g"), ingredient("小汤圆", "200", "g"), ingredient("鸡蛋", "1", "个"), ingredient("枸杞", "适量")), steps(step("锅里加水煮开，先下小汤圆"), step("汤圆浮起后放酒酿再煮两分钟", 120, "STEW"), step("淋入蛋液形成细蛋花"), step("最后撒枸杞，喜欢更甜可补少量糖"))),
    recipe("南瓜饼", "甜品", "外层微脆、里面软糯，是家庭版高成功率点心", 3, 30, ingredients(ingredient("南瓜", "300", "g"), ingredient("糯米粉", "200", "g"), ingredient("白糖", "2", "勺"), ingredient("面包糠", "适量")), steps(step("南瓜蒸熟压泥，趁热加白糖和糯米粉揉成团", 900, "STEW"), step("分成小剂子压扁，表面裹面包糠"), step("平底锅少油小火煎到两面金黄", 420, "STIR_FRY"))),
    recipe("双皮奶", "甜品", "步骤看着多，但按顺序做并不难", 2, 40, ingredients(ingredient("牛奶", "500", "ml"), ingredient("蛋清", "2", "个"), ingredient("白糖", "30", "g")), steps(step("牛奶加热到边缘起小泡后倒入碗里放凉", 300, "STEW"), step("等表面结奶皮后，从边缘开小口把奶倒回锅里，奶皮留在碗里"), step("蛋清加白糖打散，倒入温牛奶并过滤"), step("把混合液沿着筷子缓慢倒回留有奶皮的碗里"), step("盖保鲜膜扎小孔，蒸十五分钟后焖五分钟", 1200, "STEW"))),
    recipe("牛奶炖蛋", "甜品", "非常适合给孩子做，口感细滑", 2, 20, ingredients(ingredient("牛奶", "250", "ml"), ingredient("鸡蛋", "2", "个"), ingredient("白糖", "适量")), steps(step("鸡蛋打散后和牛奶、白糖拌匀并过滤"), step("倒入碗里，盖保鲜膜扎小孔"), step("水开后上锅蒸十二分钟，再焖三分钟", 900, "STEW"))),
    recipe("银耳羹", "甜品", "耐心煮出胶，口感会明显提升", 3, 50, ingredients(ingredient("银耳", "半", "朵"), ingredient("冰糖", "适量"), ingredient("红枣", "6", "颗")), steps(step("银耳泡发后去根撕小朵"), step("加足量水先煮三十分钟到开始出胶", 1800, "STEW"), step("放红枣和冰糖继续煮到浓稠顺滑", 900, "STEW"))),
    recipe("红豆薏米羹", "甜品", "提前浸泡后更容易煮软煮糯", 3, 55, ingredients(ingredient("红豆", "80", "g"), ingredient("薏米", "60", "g"), ingredient("冰糖", "适量")), steps(step("红豆和薏米提前泡两小时", 7200, "MARINATE"), step("加足量水先大火煮开，再小火慢煮", 2400, "STEW"), step("煮到豆子开花后放冰糖，再煮十分钟", 600, "STEW"))),
    recipe("桂花山药", "甜品", "清甜不腻，适合饭后小份上桌", 2, 18, ingredients(ingredient("铁棍山药", "2", "根"), ingredient("桂花蜜", "2", "勺"), ingredient("蓝莓酱", "1", "勺")), steps(step("山药蒸熟后去皮压成泥", 900, "STEW"), step("装进模具或整形成小段"), step("淋桂花蜜，喜欢的话再点少许蓝莓酱"))),
    recipe("红枣发糕", "甜品", "不用太复杂的整形，重点是发酵到位", 4, 90, ingredients(ingredient("面粉", "250", "g"), ingredient("酵母", "3", "g"), ingredient("红枣", "10", "颗"), ingredient("温水", "适量"), ingredient("白糖", "20", "g")), steps(step("面粉、酵母、白糖和温水调成偏稠面糊"), step("倒入模具后放温暖处发酵到明显涨大", 3600, "MARINATE"), step("表面摆上去核红枣"), step("水开后蒸二十五分钟，再焖五分钟", 1800, "STEW"))),
    recipe("烤年糕", "小吃", "外壳微脆、里面软糯，非常适合当下午加餐", 2, 15, ingredients(ingredient("年糕片", "250", "g"), ingredient("蜂蜜", "1", "勺"), ingredient("熟黄豆粉", "适量")), steps(step("年糕片表面轻刷一点油"), step("空气炸锅或烤箱烤到膨起微黄", 600, "STEW"), step("取出刷少量蜂蜜，再撒黄豆粉")))
)

private fun proteinWok(
    spec: ProteinWokSpec,
    proteinName: String,
    proteinCut: String
): DefaultRecipeSeed {
    val vegetablesText = spec.vegetables.joinToString("、") { it.name }
    val aromaticsText = spec.aromatics.joinToString("、") { it.name }
    val sauceText = spec.sauce.joinToString("、") { it.name }
    return recipe(
        name = spec.name,
        category = "荤菜",
        description = spec.description,
        servings = spec.servings,
        totalTime = spec.totalTime,
        ingredients = mergeIngredients(
            listOf(ingredient(proteinName, spec.proteinQuantity, "g")),
            spec.vegetables,
            spec.aromatics,
            listOf(ingredient("料酒", "1", "勺"), ingredient("淀粉", "1", "小勺")),
            spec.sauce
        ),
        steps = steps(
            step("$proteinName$proteinCut，加料酒、少许生抽和淀粉抓匀，先腌五分钟", 300, "MARINATE"),
            step("${vegetablesText}全部洗净切好，容易出水的食材单独放"),
            step("取小碗先调好$sauceText，下锅后翻炒会更从容"),
            step("热锅下油，把${proteinName}快速滑散到刚变色后先盛出", 60, "STIR_FRY"),
            step("锅里留底油，爆香$aromaticsText", 20, "STIR_FRY"),
            step("放入${vegetablesText}大火翻炒到断生", 90, "STIR_FRY"),
            step("倒回${proteinName}，沿锅边淋入碗汁快速翻匀", 45, "STIR_FRY"),
            step(spec.finishNote)
        )
    )
}

private fun eggDish(spec: EggDishSpec): DefaultRecipeSeed {
    val vegetablesText = spec.vegetables.joinToString("、") { it.name }
    val aromaticsText = spec.aromatics.joinToString("、") { it.name }.ifBlank { "葱花" }
    return recipe(
        name = spec.name,
        category = "素菜",
        description = spec.description,
        servings = 2,
        totalTime = spec.totalTime,
        ingredients = mergeIngredients(
            listOf(ingredient("鸡蛋", spec.eggs, "个")),
            spec.vegetables,
            spec.aromatics,
            spec.sauce
        ),
        steps = steps(
            step("鸡蛋打散，加一小撮盐搅匀；${vegetablesText}处理好备用"),
            step("热锅稍微多放一点油，把鸡蛋炒到八成熟先盛出", 60, "STIR_FRY"),
            step("锅里留底油，爆香$aromaticsText", 20, "STIR_FRY"),
            step("放入${vegetablesText}翻炒到合适熟度", 90, "STIR_FRY"),
            step("倒回鸡蛋，按口味补盐后快速翻匀", 30, "STIR_FRY"),
            step(spec.finishNote)
        )
    )
}

private fun vegetableDish(spec: VegetableDishSpec): DefaultRecipeSeed {
    val vegetablesText = spec.vegetables.joinToString("、") { it.name }
    val aromaticsText = spec.aromatics.joinToString("、") { it.name }
    val steps = buildList {
        add(step("${vegetablesText}全部洗净切好，先把难熟和易熟的食材分开"))
        spec.blanchSeconds?.let {
            add(step("锅里水开后把主料快速焯一下再捞出，后面更容易掌握火候", it, "BLANCH"))
        }
        add(step("热锅下油，先把${aromaticsText}炒出香味", 20, "STIR_FRY"))
        add(step("放入${vegetablesText}大火翻炒，让蔬菜尽快受热均匀", 90, "STIR_FRY"))
        add(step("按口味加入调味料继续翻匀，必要时沿锅边补一小勺热水", 45, "STIR_FRY"))
        add(step(spec.finishNote))
    }
    return recipe(
        name = spec.name,
        category = "素菜",
        description = spec.description,
        servings = 2,
        totalTime = spec.totalTime,
        ingredients = mergeIngredients(spec.vegetables, spec.aromatics, spec.seasonings),
        steps = steps(*steps.toTypedArray())
    )
}

private fun soupRecipe(
    name: String,
    description: String,
    totalTime: Int,
    ingredients: List<DefaultIngredientSeed>,
    steps: List<DefaultStepSeed>
) = recipe(name, "汤类", description, 2, totalTime, ingredients, steps)

private fun stapleRecipe(
    name: String,
    category: String,
    description: String,
    servings: Int,
    totalTime: Int,
    ingredients: List<DefaultIngredientSeed>,
    steps: List<DefaultStepSeed>
) = recipe(name, category, description, servings, totalTime, ingredients, steps)

private fun recipe(
    name: String,
    category: String,
    description: String,
    servings: Int,
    totalTime: Int,
    ingredients: List<DefaultIngredientSeed>,
    steps: List<DefaultStepSeed>
) = DefaultRecipeSeed(
    recipe = RecipeEntity(
        name = name,
        category = category,
        description = description,
        servings = servings,
        totalTime = totalTime
    ),
    ingredients = ingredients,
    steps = steps
)

private fun mergeIngredients(vararg groups: List<DefaultIngredientSeed>): List<DefaultIngredientSeed> {
    val seen = linkedSetOf<String>()
    return buildList {
        groups.forEach { group ->
            group.forEach { item ->
                if (seen.add(item.name)) add(item)
            }
        }
    }
}

private fun ingredients(vararg values: DefaultIngredientSeed) = values.toList()

private fun ingredient(
    name: String,
    quantity: String,
    unit: String = ""
) = DefaultIngredientSeed(name = name, quantity = quantity, unit = unit)

private fun steps(vararg values: DefaultStepSeed) = values.toList()

private fun step(
    description: String,
    timerSeconds: Int? = null,
    timerType: String? = null
) = DefaultStepSeed(description = description, timerSeconds = timerSeconds, timerType = timerType)
