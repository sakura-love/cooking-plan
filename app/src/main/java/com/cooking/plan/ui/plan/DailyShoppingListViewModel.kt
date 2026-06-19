package com.cooking.plan.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.DailyShoppingCheckEntity
import com.cooking.plan.data.local.IngredientEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DailyShoppingListViewModel @Inject constructor(
    private val dao: AppDao
) : ViewModel() {
    val allRecipes = dao.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _ingredientsByRecipe = MutableStateFlow<Map<Long, List<IngredientEntity>>>(emptyMap())
    val ingredientsByRecipe: StateFlow<Map<Long, List<IngredientEntity>>> = _ingredientsByRecipe

    fun getPlans(year: Int, weekNumber: Int) = dao.getPlansForWeek(year, weekNumber)

    fun getChecks(year: Int, weekNumber: Int, dayOfWeek: Int) =
        dao.getDailyShoppingChecks(year, weekNumber, dayOfWeek)

    fun loadIngredients(recipeIds: List<Long>) {
        viewModelScope.launch {
            _ingredientsByRecipe.value = recipeIds.associateWith { recipeId ->
                dao.getIngredientsForRecipeOnce(recipeId)
            }
        }
    }

    fun setChecked(year: Int, weekNumber: Int, dayOfWeek: Int, itemKey: String, checked: Boolean) {
        viewModelScope.launch {
            dao.upsertDailyShoppingCheck(
                DailyShoppingCheckEntity(
                    year = year,
                    weekNumber = weekNumber,
                    dayOfWeek = dayOfWeek,
                    itemKey = itemKey,
                    checked = checked
                )
            )
        }
    }
}
