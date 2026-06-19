package com.cooking.plan.ui.recipe

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.ai.AiRecipeService
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.IngredientEntity
import com.cooking.plan.data.local.RecipeEntity
import com.cooking.plan.data.local.StepEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

data class IngredientInput(
    val name: String = "",
    val quantity: String = "",
    val unit: String = ""
)

data class StepInput(
    val description: String = "",
    val timerSeconds: Int? = null,
    val timerType: String? = null
)

@HiltViewModel
class AddRecipeViewModel @Inject constructor(
    private val dao: AppDao,
    private val aiRecipeService: AiRecipeService
) : ViewModel() {

    private val _name = MutableStateFlow("")
    val name: StateFlow<String> = _name

    private val _category = MutableStateFlow("荤菜")
    val category: StateFlow<String> = _category

    private val _description = MutableStateFlow("")
    val description: StateFlow<String> = _description

    private val _servings = MutableStateFlow(2)
    val servings: StateFlow<Int> = _servings

    private val _totalTime = MutableStateFlow(0)
    val totalTime: StateFlow<Int> = _totalTime

    private val _imageUri = MutableStateFlow<String?>(null)
    val imageUri: StateFlow<String?> = _imageUri

    private val _ingredients = MutableStateFlow<List<IngredientInput>>(listOf(IngredientInput()))
    val ingredients: StateFlow<List<IngredientInput>> = _ingredients

    private val _steps = MutableStateFlow<List<StepInput>>(listOf(StepInput()))
    val steps: StateFlow<List<StepInput>> = _steps

    private val _saved = MutableSharedFlow<Long>()
    val saved: SharedFlow<Long> = _saved

    private val _isGenerating = MutableStateFlow(false)
    val isGenerating: StateFlow<Boolean> = _isGenerating

    private val _errorMessage = MutableSharedFlow<String>()
    val errorMessage: SharedFlow<String> = _errorMessage

    private var editingRecipeId: Long? = null

    val categories = listOf("荤菜", "素菜", "汤类", "主食", "甜品", "凉菜", "小吃")

    fun loadRecipe(recipeId: Long) {
        editingRecipeId = recipeId
        viewModelScope.launch {
            val recipe = dao.getRecipeById(recipeId) ?: return@launch
            _name.value = recipe.name
            _category.value = recipe.category
            _description.value = recipe.description
            _servings.value = recipe.servings
            _totalTime.value = recipe.totalTime
            _imageUri.value = recipe.imageUri.ifEmpty { null }

            val ingredients = dao.getIngredientsForRecipeOnce(recipeId)
            if (ingredients.isNotEmpty()) {
                _ingredients.value = ingredients.map {
                    IngredientInput(it.name, it.quantity, it.unit)
                }
            }

            val steps = dao.getStepsForRecipeOnce(recipeId)
            if (steps.isNotEmpty()) {
                _steps.value = steps.map {
                    StepInput(it.description, it.timerSeconds, it.timerType)
                }
            }
        }
    }

    fun updateName(v: String) { _name.value = v }
    fun updateCategory(v: String) { _category.value = v }
    fun updateDescription(v: String) { _description.value = v }
    fun updateServings(v: Int) { _servings.value = v.coerceIn(1, 20) }
    fun updateTotalTime(v: Int) { _totalTime.value = v.coerceIn(0, 999) }
    fun updateImageUri(v: String?) { _imageUri.value = v }

    fun updateIngredient(index: Int, input: IngredientInput) {
        _ingredients.value = _ingredients.value.toMutableList().also { it[index] = input }
    }

    fun addIngredient() {
        _ingredients.value = _ingredients.value + IngredientInput()
    }

    fun removeIngredient(index: Int) {
        _ingredients.value = _ingredients.value.toMutableList().also { it.removeAt(index) }
    }

    fun updateStep(index: Int, input: StepInput) {
        _steps.value = _steps.value.toMutableList().also { it[index] = input }
    }

    fun addStep() {
        _steps.value = _steps.value + StepInput()
    }

    fun removeStep(index: Int) {
        _steps.value = _steps.value.toMutableList().also { it.removeAt(index) }
    }

    fun save() {
        if (_name.value.isBlank()) return
        viewModelScope.launch {
            val recipe = RecipeEntity(
                id = editingRecipeId ?: 0,
                name = _name.value.trim(),
                category = _category.value,
                description = _description.value.trim(),
                imageUri = _imageUri.value ?: "",
                servings = _servings.value,
                totalTime = _totalTime.value
            )
            val recipeId = dao.insertRecipe(recipe)

            // Replace ingredients
            dao.deleteIngredientsForRecipe(recipeId)
            val validIngredients = _ingredients.value.filter { it.name.isNotBlank() }
            if (validIngredients.isNotEmpty()) {
                dao.insertIngredients(validIngredients.map {
                    IngredientEntity(recipeId = recipeId, name = it.name, quantity = it.quantity, unit = it.unit)
                })
            }

            // Replace steps
            dao.deleteStepsForRecipe(recipeId)
            val validSteps = _steps.value.filter { it.description.isNotBlank() }
            if (validSteps.isNotEmpty()) {
                dao.insertSteps(validSteps.mapIndexed { idx, s ->
                    StepEntity(
                        recipeId = recipeId,
                        order = idx + 1,
                        description = s.description,
                        timerSeconds = s.timerSeconds,
                        timerType = s.timerType
                    )
                })
            }

            _saved.emit(recipeId)
        }
    }

    fun generateAndSave() {
        val recipeName = _name.value.trim()
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
                    _saved.emit(recipeId)
                }
                .onFailure { error ->
                    _errorMessage.emit(error.message ?: "AI 生成失败")
                }
            _isGenerating.value = false
        }
    }

    fun deleteRecipe() {
        val id = editingRecipeId ?: return
        viewModelScope.launch {
            dao.deleteRecipeById(id)
            _saved.emit(-1)
        }
    }
}
