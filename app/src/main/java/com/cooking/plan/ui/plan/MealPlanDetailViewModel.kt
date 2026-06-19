package com.cooking.plan.ui.plan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.WeeklyPlanEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MealPlanDetailViewModel @Inject constructor(
    private val dao: AppDao
) : ViewModel() {
    val allRecipes = dao.getAllRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun getPlans(year: Int, weekNumber: Int) = dao.getPlansForWeek(year, weekNumber)

    fun removePlan(plan: WeeklyPlanEntity) {
        viewModelScope.launch {
            dao.deletePlan(plan)
        }
    }
}
