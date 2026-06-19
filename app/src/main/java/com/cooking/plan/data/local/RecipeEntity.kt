package com.cooking.plan.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val category: String,
    val description: String = "",
    val imageUri: String = "",
    val servings: Int = 2,
    val totalTime: Int = 0,
    val isFavorite: Boolean = false
)

@Entity(
    tableName = "ingredients",
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("recipeId")]
)
data class IngredientEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val name: String,
    val quantity: String = "",
    val unit: String = ""
)

@Entity(
    tableName = "steps",
    foreignKeys = [ForeignKey(
        entity = RecipeEntity::class,
        parentColumns = ["id"],
        childColumns = ["recipeId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index("recipeId")]
)
data class StepEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long,
    val order: Int,
    val description: String,
    val timerSeconds: Int? = null,
    val timerType: String? = null // STIR_FRY, MARINATE, STEW, BLANCH, CUSTOM
)

@Entity(tableName = "weekly_plans")
data class WeeklyPlanEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val year: Int,
    val weekNumber: Int,
    val dayOfWeek: Int, // 1=Monday..7=Sunday
    val meal: String, // BREAKFAST, LUNCH, DINNER
    val recipeId: Long
)

@Entity(
    tableName = "shopping_items",
    indices = [Index("recipeId")]
)
data class ShoppingItemEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val recipeId: Long? = null,
    val name: String,
    val quantity: String = "",
    val unit: String = "",
    val checked: Boolean = false
)

@Entity(
    tableName = "daily_shopping_checks",
    indices = [Index(value = ["year", "weekNumber", "dayOfWeek", "itemKey"], unique = true)]
)
data class DailyShoppingCheckEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val year: Int,
    val weekNumber: Int,
    val dayOfWeek: Int,
    val itemKey: String,
    val checked: Boolean = false
)
