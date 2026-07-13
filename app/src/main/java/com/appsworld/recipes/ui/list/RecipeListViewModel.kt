package com.appsworld.recipes.ui.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.usecase.GetRecipesSortedByTime
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface RecipeListUiState {

    data object Loading : RecipeListUiState

    data class Success(val recipes: List<Recipe>) : RecipeListUiState

    data class Error(val message: String) : RecipeListUiState
}

@HiltViewModel
class RecipeListViewModel @Inject constructor(
    private val getRecipesSortedByTime: GetRecipesSortedByTime,
) : ViewModel() {

    private val _uiState = MutableStateFlow<RecipeListUiState>(RecipeListUiState.Loading)
    val uiState: StateFlow<RecipeListUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            _uiState.value = try {
                RecipeListUiState.Success(getRecipesSortedByTime.getRecipesSortedByTime())
            } catch (e: Exception) {
                RecipeListUiState.Error("Couldn't load recipes")
            }
        }
    }
}
