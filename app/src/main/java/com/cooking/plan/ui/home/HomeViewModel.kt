package com.cooking.plan.ui.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.ai.AiRecipeService
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.IngredientEntity
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.data.local.StepEntity
import com.cooking.plan.data.settings.AppSettingsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@HiltViewModel
class HomeViewModel @Inject constructor(
    private val dao: AppDao,
    private val settingsRepository: AppSettingsRepository,
    private val aiRecipeService: AiRecipeService
) : ViewModel() {
    private val categoryOrder = listOf("荤菜", "素菜", "汤类", "主食", "凉菜", "小吃", "甜品")


    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory

    private val _events = MutableSharedFlow<HomeEvent>()
    val events: SharedFlow<HomeEvent> = _events.asSharedFlow()

    private val allRecipes: Flow<List<RecipeEntity>> = _searchQuery.flatMapLatest { query ->
        if (query.isBlank()) dao.getAllRecipes() else dao.searchRecipes(query)
    }

    val recipes: Flow<List<RecipeEntity>> = combine(allRecipes, _selectedCategory) { recipes, selectedCategory ->
        val filtered = if (selectedCategory.isNullOrBlank()) {
            recipes
        } else {
            recipes.filter { it.category == selectedCategory }
        }
        filtered.sortedWith(
            compareBy<RecipeEntity> { categoryOrder.indexOf(it.category).takeIf { index -> index >= 0 } ?: Int.MAX_VALUE }
                .thenBy { it.name }
        )
    }

    val categoryTabs: Flow<List<CategoryTabState>> = combine(dao.getAllRecipes(), _selectedCategory) { recipes, selectedCategory ->
        val counts = recipes.groupingBy { it.category }.eachCount()
        buildList {
            add(CategoryTabState(label = "全部", count = recipes.size, selected = selectedCategory == null))
            counts.entries
                .sortedWith(
                    compareBy<Map.Entry<String, Int>> { categoryOrder.indexOf(it.key).takeIf { index -> index >= 0 } ?: Int.MAX_VALUE }
                        .thenBy { it.key }
                )
                .forEach { (category, count) ->
                    add(CategoryTabState(label = category, count = count, selected = selectedCategory == category))
                }
        }
    }

    val groupedRecipes: Flow<List<HomeRecipeGroup>> = recipes.map { recipes ->
        recipes
            .groupBy { it.category }
            .entries
            .sortedBy { categoryOrder.indexOf(it.key).takeIf { index -> index >= 0 } ?: Int.MAX_VALUE }
            .map { (category, items) ->
                HomeRecipeGroup(category = category, recipes = items.sortedBy { it.name })
            }
    }

    val activeCookingRecipe: Flow<RecipeEntity?> = settingsRepository.settings.flatMapLatest { settings ->
        if (settings.activeCookingRecipeId > 0) {
            dao.getRecipeByIdFlow(settings.activeCookingRecipeId)
        } else {
            kotlinx.coroutines.flow.flowOf(null)
        }
    }

    val activeCookingRecipeId: Flow<Long> = settingsRepository.settings.map { it.activeCookingRecipeId }

    fun updateSearch(query: String) {
        _searchQuery.value = query
    }

    fun selectCategory(category: String?) {
        _selectedCategory.value = category
    }

    fun deleteRecipe(id: Long) {
        viewModelScope.launch { dao.deleteRecipeById(id) }
    }

    fun toggleFavorite(recipe: RecipeEntity) {
        viewModelScope.launch { dao.setFavorite(recipe.id, !recipe.isFavorite) }
    }

    fun clearActiveCooking(recipeId: Long) {
        settingsRepository.clearActiveCookingRecipe(recipeId)
    }

    fun generateRecipeFromSearch() {
        val recipeName = _searchQuery.value.trim()
        if (recipeName.isBlank() || _isGenerating.value) return
        viewModelScope.launch {
            _isGenerating.value = true
            aiRecipeService.generateRecipe(recipeName)
                .onSuccess { generated ->
                    val recipeId = dao.insertRecipe(generated.recipe)
                    dao.insertIngredients(generated.ingredients.map {
                        IngredientEntity(recipeId = recipeId, name = it.name, quantity = it.quantity, unit = it.unit)
                    })
                    dao.insertSteps(generated.steps.mapIndexed { index, step ->
                        StepEntity(
                            recipeId = recipeId,
                            order = index + 1,
                            description = step.description,
                            timerSeconds = step.timerSeconds,
                            timerType = step.timerType
                        )
                    })
                    _searchQuery.value = ""
                    _events.emit(HomeEvent.OpenRecipe(recipeId))
                }
                .onFailure { error ->
                    _events.emit(HomeEvent.ShowMessage(error.message ?: "AI 生成失败"))
                }
            _isGenerating.value = false
        }
    }
}

data class HomeRecipeGroup(
    val category: String,
    val recipes: List<RecipeEntity>
)

data class CategoryTabState(
    val label: String,
    val count: Int,
    val selected: Boolean
)

sealed interface HomeEvent {
    data class OpenRecipe(val recipeId: Long) : HomeEvent
    data class ShowMessage(val message: String) : HomeEvent
}
