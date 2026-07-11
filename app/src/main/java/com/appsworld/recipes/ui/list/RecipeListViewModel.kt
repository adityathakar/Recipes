package com.appsworld.recipes.ui.list

import androidx.lifecycle.ViewModel
import com.appsworld.recipes.data.Recipe
import com.appsworld.recipes.data.RecipeRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

data class RecipeListUiState(
    val recipes: List<Recipe> = emptyList(),
)

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    repository: RecipeRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(RecipeListUiState(recipes = repository.getRecipes()))
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()
}
