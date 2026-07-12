package com.appsworld.recipes.ui.detail

import androidx.lifecycle.ViewModel
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.repository.GetRecipe
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

data class RecipeDetailUiState(
    val recipe: Recipe? = null,
)

@HiltViewModel(assistedFactory = RecipeDetailViewModel.Factory::class)
class RecipeDetailViewModel @AssistedInject constructor(
    @Assisted private val recipeId: String,
    getRecipe: GetRecipe,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(recipeId: String): RecipeDetailViewModel
    }

    private val _uiState = MutableStateFlow(RecipeDetailUiState(recipe = getRecipe.getRecipe(recipeId)))
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()
}
