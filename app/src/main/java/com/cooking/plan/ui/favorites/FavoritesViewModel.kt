package com.cooking.plan.ui.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cooking.plan.data.local.AppDao
import com.cooking.plan.data.local.RecipeEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val dao: AppDao
) : ViewModel() {

    val favorites = dao.getFavoriteRecipes()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeFavorite(recipe: RecipeEntity) {
        viewModelScope.launch { dao.setFavorite(recipe.id, false) }
    }
}
