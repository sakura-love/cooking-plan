package com.cooking.plan.ui.shopping

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.IngredientEntity
import com.cooking.plan.data.local.ShoppingItemEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingViewModel @Inject constructor(
    private val dao: AppDao
) : ViewModel() {

    val items = dao.getAllShoppingItems()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun addItem(name: String, quantity: String, unit: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            dao.insertShoppingItem(ShoppingItemEntity(name = name.trim(), quantity = quantity.trim(), unit = unit.trim()))
        }
    }

    fun toggleItem(item: ShoppingItemEntity) {
        viewModelScope.launch { dao.updateShoppingItem(item.copy(checked = !item.checked)) }
    }

    fun deleteItem(item: ShoppingItemEntity) {
        viewModelScope.launch { dao.deleteShoppingItem(item) }
    }

    fun clearChecked() {
        viewModelScope.launch { dao.deleteCheckedItems() }
    }

    fun clearAll() {
        viewModelScope.launch { dao.clearShoppingList() }
    }

    fun importFromRecipe(recipeId: Long) {
        viewModelScope.launch {
            val ingredients = dao.getIngredientsForRecipeOnce(recipeId)
            val shoppingItems = ingredients.map { ing ->
                ShoppingItemEntity(
                    recipeId = recipeId,
                    name = ing.name,
                    quantity = ing.quantity,
                    unit = ing.unit
                )
            }
            dao.insertShoppingItems(shoppingItems)
        }
    }
}
