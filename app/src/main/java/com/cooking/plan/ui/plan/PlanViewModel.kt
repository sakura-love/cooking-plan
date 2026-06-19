package com.cooking.plan.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.data.local.WeeklyPlanEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.temporal.WeekFields
import java.util.Locale
import javax.inject.Inject

@HiltViewModel
class PlanViewModel @Inject constructor(
    private val dao: AppDao
) : ViewModel() {

    private val today = LocalDate.now()
    private val weekFields = WeekFields.of(Locale.getDefault())
    private val currentYear = today.year
    private val currentWeek = today.get(weekFields.weekOfWeekBasedYear())
    private val currentDayOfWeek = today.dayOfWeek.value // 1=Mon..7=Sun

    val year = currentYear
    val weekNumber = currentWeek

    val plans = dao.getPlansForWeek(currentYear, currentWeek)
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val allRecipes = dao.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    private val _showRecipePicker = MutableStateFlow<PlanSlot?>(null)
    val showRecipePicker: StateFlow<PlanSlot?> = _showRecipePicker

    fun getCurrentDayOfWeek() = currentDayOfWeek

    fun openRecipePicker(dayOfWeek: Int, meal: String) {
        _showRecipePicker.value = PlanSlot(dayOfWeek, meal)
    }

    fun closeRecipePicker() {
        _showRecipePicker.value = null
    }

    fun assignRecipe(dayOfWeek: Int, meal: String, recipeId: Long) {
        viewModelScope.launch {
            val existing = dao.getPlanForRecipe(currentYear, currentWeek, dayOfWeek, meal, recipeId)
            if (existing == null) {
                dao.insertPlan(
                    WeeklyPlanEntity(
                        year = currentYear,
                        weekNumber = currentWeek,
                        dayOfWeek = dayOfWeek,
                        meal = meal,
                        recipeId = recipeId
                    )
                )
            }
        }
    }

    fun removePlan(dayOfWeek: Int, meal: String, recipeId: Long) {
        viewModelScope.launch {
            dao.deletePlanRecipe(currentYear, currentWeek, dayOfWeek, meal, recipeId)
        }
    }
}

data class PlanSlot(val dayOfWeek: Int, val meal: String)
