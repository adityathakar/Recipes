package com.appsworld.recipes.ui.detail

import com.appsworld.recipes.MainDispatcherRule
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.repository.GetRecipe
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeDetailViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `is loading before the recipe arrives`() = runTest {
        val viewModel = RecipeDetailViewModel(recipe.id, GetRecipe { recipe })

        assertEquals(RecipeDetailUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `emits the recipe matching the id it was created with`() = runTest {
        val viewModel = RecipeDetailViewModel("3") { id -> recipe.takeIf { it.id == id } }

        advanceUntilIdle()

        assertEquals(RecipeDetailUiState.Success(recipe), viewModel.uiState.value)
    }

    @Test
    fun `emits an error when the recipe does not exist`() = runTest {
        val viewModel = RecipeDetailViewModel("nope", GetRecipe { null })

        advanceUntilIdle()

        assertEquals(RecipeDetailUiState.Error("Recipe not found"), viewModel.uiState.value)
    }

    @Test
    fun `emits an error when loading fails`() = runTest {
        val viewModel = RecipeDetailViewModel(recipe.id, GetRecipe { throw IOException("no assets") })

        advanceUntilIdle()

        assertEquals(RecipeDetailUiState.Error("Couldn't load recipe"), viewModel.uiState.value)
    }
}

private val recipe = Recipe(
    id = "3",
    title = "Guacamole",
    description = "Chunky, lime-forward, ready in ten minutes.",
    numOfServes = 6,
    prepTimeInMins = 10,
    cookTimeInMins = 0,
    imageUrl = "",
    ingredients = listOf("3 ripe avocados", "1 lime, juiced"),
)
