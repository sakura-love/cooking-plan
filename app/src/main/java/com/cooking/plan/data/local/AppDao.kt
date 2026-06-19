package com.cooking.plan.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface AppDao {
    // ── Recipes ──
    @Query("SELECT * FROM recipes ORDER BY name")
    fun getAllRecipes(): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes ORDER BY name")
    suspend fun getAllRecipesOnce(): List<RecipeEntity>

    @Query("SELECT COUNT(*) FROM recipes")
    suspend fun getRecipeCount(): Int

    @Query("SELECT * FROM recipes WHERE category = :category ORDER BY name")
    fun getRecipesByCategory(category: String): Flow<List<RecipeEntity>>

    @Query(
        """
        SELECT DISTINCT recipes.* FROM recipes
        LEFT JOIN ingredients ON ingredients.recipeId = recipes.id
        WHERE recipes.name LIKE '%' || :query || '%'
           OR recipes.description LIKE '%' || :query || '%'
           OR recipes.category LIKE '%' || :query || '%'
           OR ingredients.name LIKE '%' || :query || '%'
        ORDER BY recipes.name
        """
    )
    fun searchRecipes(query: String): Flow<List<RecipeEntity>>

    @Query("SELECT * FROM recipes WHERE id = :id")
    suspend fun getRecipeById(id: Long): RecipeEntity?

    @Query("SELECT * FROM recipes WHERE id = :id")
    fun getRecipeByIdFlow(id: Long): Flow<RecipeEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipe: RecipeEntity): Long

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipes WHERE id = :id")
    suspend fun deleteRecipeById(id: Long)

    @Query("UPDATE recipes SET isFavorite = :isFavorite WHERE id = :recipeId")
    suspend fun setFavorite(recipeId: Long, isFavorite: Boolean)

    @Query("SELECT * FROM recipes WHERE isFavorite = 1 ORDER BY name")
    fun getFavoriteRecipes(): Flow<List<RecipeEntity>>

    // ── Ingredients ──
    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId ORDER BY id")
    fun getIngredientsForRecipe(recipeId: Long): Flow<List<IngredientEntity>>

    @Query("SELECT * FROM ingredients WHERE recipeId = :recipeId ORDER BY id")
    suspend fun getIngredientsForRecipeOnce(recipeId: Long): List<IngredientEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertIngredients(ingredients: List<IngredientEntity>)

    @Query("DELETE FROM ingredients WHERE recipeId = :recipeId")
    suspend fun deleteIngredientsForRecipe(recipeId: Long)

    // ── Steps ──
    @Query("SELECT * FROM steps WHERE recipeId = :recipeId ORDER BY `order`")
    fun getStepsForRecipe(recipeId: Long): Flow<List<StepEntity>>

    @Query("SELECT * FROM steps WHERE recipeId = :recipeId ORDER BY `order`")
    suspend fun getStepsForRecipeOnce(recipeId: Long): List<StepEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSteps(steps: List<StepEntity>)

    @Query("DELETE FROM steps WHERE recipeId = :recipeId")
    suspend fun deleteStepsForRecipe(recipeId: Long)

    // ── Weekly Plans ──
    @Query("SELECT * FROM weekly_plans WHERE year = :year AND weekNumber = :weekNumber ORDER BY dayOfWeek, meal")
    fun getPlansForWeek(year: Int, weekNumber: Int): Flow<List<WeeklyPlanEntity>>

    @Query("SELECT * FROM weekly_plans WHERE year = :year AND weekNumber = :weekNumber AND dayOfWeek = :dayOfWeek AND meal = :meal")
    suspend fun getPlan(year: Int, weekNumber: Int, dayOfWeek: Int, meal: String): WeeklyPlanEntity?

    @Query("SELECT * FROM weekly_plans WHERE year = :year AND weekNumber = :weekNumber AND dayOfWeek = :dayOfWeek AND meal = :meal AND recipeId = :recipeId LIMIT 1")
    suspend fun getPlanForRecipe(year: Int, weekNumber: Int, dayOfWeek: Int, meal: String, recipeId: Long): WeeklyPlanEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertPlan(plan: WeeklyPlanEntity)

    @Delete
    suspend fun deletePlan(plan: WeeklyPlanEntity)

    @Query("DELETE FROM weekly_plans WHERE year = :year AND weekNumber = :weekNumber AND dayOfWeek = :dayOfWeek AND meal = :meal")
    suspend fun deletePlanBySlot(year: Int, weekNumber: Int, dayOfWeek: Int, meal: String)

    @Query("DELETE FROM weekly_plans WHERE year = :year AND weekNumber = :weekNumber AND dayOfWeek = :dayOfWeek AND meal = :meal AND recipeId = :recipeId")
    suspend fun deletePlanRecipe(year: Int, weekNumber: Int, dayOfWeek: Int, meal: String, recipeId: Long)

    // ── Shopping ──
    @Query("SELECT * FROM shopping_items ORDER BY checked, name")
    fun getAllShoppingItems(): Flow<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItem(item: ShoppingItemEntity): Long

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertShoppingItems(items: List<ShoppingItemEntity>)

    @Update
    suspend fun updateShoppingItem(item: ShoppingItemEntity)

    @Delete
    suspend fun deleteShoppingItem(item: ShoppingItemEntity)

    @Query("DELETE FROM shopping_items WHERE checked = 1")
    suspend fun deleteCheckedItems()

    @Query("DELETE FROM shopping_items")
    suspend fun clearShoppingList()

    // ── Daily Plan Shopping Checks ──
    @Query("SELECT * FROM daily_shopping_checks WHERE year = :year AND weekNumber = :weekNumber AND dayOfWeek = :dayOfWeek")
    fun getDailyShoppingChecks(year: Int, weekNumber: Int, dayOfWeek: Int): Flow<List<DailyShoppingCheckEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsertDailyShoppingCheck(check: DailyShoppingCheckEntity)

    // ── Search ──
    @Query("SELECT DISTINCT name FROM ingredients ORDER BY name")
    fun getAllIngredientNames(): Flow<List<String>>
}
