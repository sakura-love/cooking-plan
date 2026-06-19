package com.cooking.plan.data.local

internal object DefaultRecipes {
    val all: List<DefaultRecipeSeed> = ExpandedDefaultRecipes.all
}

internal data class DefaultRecipeSeed(
    val recipe: RecipeEntity,
    val ingredients: List<DefaultIngredientSeed>,
    val steps: List<DefaultStepSeed>
)

internal data class DefaultIngredientSeed(
    val name: String,
    val quantity: String = "",
    val unit: String = ""
)

internal data class DefaultStepSeed(
    val description: String,
    val timerSeconds: Int? = null,
    val timerType: String? = null
)
