package com.cooking.plan.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(
    entities = [
        RecipeEntity::class,
        IngredientEntity::class,
        StepEntity::class,
        WeeklyPlanEntity::class,
        ShoppingItemEntity::class,
        DailyShoppingCheckEntity::class
    ],
    version = 4,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun dao(): AppDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: buildDatabase(context).also { database ->
                    INSTANCE = database
                    CoroutineScope(Dispatchers.IO).launch {
                        seedMissingRecipes(database.dao())
                    }
                }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context.applicationContext,
                AppDatabase::class.java,
                "cooking_plan.db"
            )
                .fallbackToDestructiveMigration()
                .build()
        }

        private suspend fun seedMissingRecipes(dao: AppDao) {
            val existingNames = dao.getAllRecipesOnce()
                .mapTo(linkedSetOf()) { it.name.trim() }

            DefaultRecipes.all.forEach { seed ->
                if (!existingNames.add(seed.recipe.name)) return@forEach

                val recipeId = dao.insertRecipe(seed.recipe)
                dao.insertIngredients(
                    seed.ingredients.map {
                        IngredientEntity(
                            recipeId = recipeId,
                            name = it.name,
                            quantity = it.quantity,
                            unit = it.unit
                        )
                    }
                )
                dao.insertSteps(
                    seed.steps.mapIndexed { index, step ->
                        StepEntity(
                            recipeId = recipeId,
                            order = index + 1,
                            description = step.description,
                            timerSeconds = step.timerSeconds,
                            timerType = step.timerType
                        )
                    }
                )
            }
        }
    }
}
