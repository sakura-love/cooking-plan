package com.cooking.plan.ui.recipe

import android.app.Application
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.IngredientEntity
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.data.local.StepEntity
import com.cooking.plan.data.settings.AppSettingsRepository
import com.cooking.plan.data.timer.TimerInfo
import com.cooking.plan.data.timer.TimerManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipeDetailViewModel @Inject constructor(
    application: Application,
    private val dao: AppDao,
    private val settingsRepository: AppSettingsRepository,
    private val timerManager: TimerManager
) : AndroidViewModel(application) {

    private val _recipe = MutableStateFlow<RecipeEntity?>(null)
    val recipe: StateFlow<RecipeEntity?> = _recipe

    private val _ingredients = MutableStateFlow<List<IngredientEntity>>(emptyList())
    val ingredients: StateFlow<List<IngredientEntity>> = _ingredients

    private val _steps = MutableStateFlow<List<StepEntity>>(emptyList())
    val steps: StateFlow<List<StepEntity>> = _steps

    private val _currentStepIndex = MutableStateFlow(0)
    val currentStepIndex: StateFlow<Int> = _currentStepIndex

    private val _completedSteps = MutableStateFlow<Set<Int>>(emptySet())
    val completedSteps: StateFlow<Set<Int>> = _completedSteps

    val settings = settingsRepository.settings

    // SnapshotStateMap — per-entry read tracking, only the ticking step recomposes
    val activeTimers: SnapshotStateMap<Int, TimerInfo> = mutableStateMapOf()

    private var recipeId: Long = 0
    private var observeJob: Job? = null

    fun loadRecipe(id: Long) {
        recipeId = id
        viewModelScope.launch {
            _recipe.value = dao.getRecipeById(id)
            launch { dao.getIngredientsForRecipe(id).collect { _ingredients.value = it } }
            launch { dao.getStepsForRecipe(id).collect { _steps.value = it } }
        }
        observeJob?.cancel()
        observeJob = viewModelScope.launch {
            timerManager.activeTimers.collectLatest { all ->
                // Diffed update: only touch changed entries so SnapshotStateMap
                // per-entry tracking doesn't invalidate the whole map
                val currentKeys = activeTimers.keys.toSet()
                val newKeys = all.keys
                // Remove stale
                (currentKeys - newKeys).forEach { activeTimers.remove(it) }
                // Add or update changed
                for ((key, info) in all) {
                    if (activeTimers[key] != info) {
                        activeTimers[key] = info
                    }
                }
            }
        }
    }

    fun completeStep(index: Int) {
        markCooking()
        _completedSteps.value = _completedSteps.value + index
        timerManager.stopTimer(index)
        if (index == _currentStepIndex.value && index + 1 < _steps.value.size) {
            _currentStepIndex.value = index + 1
        } else if (index == _currentStepIndex.value && index + 1 == _steps.value.size) {
            _currentStepIndex.value = _steps.value.size
        }
        if (_completedSteps.value.size >= _steps.value.size && _steps.value.isNotEmpty()) {
            settingsRepository.clearActiveCookingRecipe(recipeId)
        }
    }

    fun goToStep(index: Int) {
        if (index in 0 until _steps.value.size) {
            _currentStepIndex.value = index
        }
    }

    fun startTimer(stepIndex: Int, seconds: Int, stepLabel: String) {
        markCooking()
        timerManager.startTimer(recipeId, stepIndex, seconds, stepLabel)
    }

    fun stopTimer(stepIndex: Int) {
        timerManager.stopTimer(stepIndex)
    }

    fun toggleFavorite() {
        val r = _recipe.value ?: return
        viewModelScope.launch {
            dao.setFavorite(r.id, !r.isFavorite)
            _recipe.value = r.copy(isFavorite = !r.isFavorite)
        }
    }

    fun markCooking() {
        if (recipeId > 0) settingsRepository.setActiveCookingRecipe(recipeId)
    }

    fun finishCooking() {
        settingsRepository.clearActiveCookingRecipe(recipeId)
    }

    override fun onCleared() {
        observeJob?.cancel()
        // Timers survive ViewModel destruction via TimerManager
        super.onCleared()
    }
}
