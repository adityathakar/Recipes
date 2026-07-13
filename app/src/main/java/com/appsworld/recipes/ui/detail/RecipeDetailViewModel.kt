package com.appsworld.recipes.ui.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.repository.GetRecipe
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

sealed interface RecipeDetailUiState {

    data object Loading : RecipeDetailUiState

    data class Success(val recipe: Recipe) : RecipeDetailUiState

    data class Error(val message: String) : RecipeDetailUiState
}

@HiltViewModel(assistedFactory = RecipeDetailViewModel.Factory::class)
class RecipeDetailViewModel @AssistedInject constructor(
    @Assisted private val recipeId: String,
    private val getRecipe: GetRecipe,
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(recipeId: String): RecipeDetailViewModel
    }

    private val _uiState = MutableStateFlow<RecipeDetailUiState>(RecipeDetailUiState.Loading)
    val uiState: StateFlow<RecipeDetailUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = try {
                getRecipe.getRecipe(recipeId)
                    ?.let(RecipeDetailUiState::Success)
                    ?: RecipeDetailUiState.Error("Recipe not found")
            } catch (e: Exception) {
                RecipeDetailUiState.Error("Couldn't load recipe")
            }
        }
    }
}
