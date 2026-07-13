package com.appsworld.recipes.ui.list

import com.appsworld.recipes.MainDispatcherRule
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.repository.GetRecipes
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Rule
import org.junit.Test
import java.io.IOException

@OptIn(ExperimentalCoroutinesApi::class)
class RecipeListViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Test
    fun `is loading before the recipes arrive`() = runTest {
        val viewModel = RecipeListViewModel(GetRecipes { recipes })

        assertEquals(RecipeListUiState.Loading, viewModel.uiState.value)
    }

    @Test
    fun `emits the loaded recipes`() = runTest {
        val viewModel = RecipeListViewModel(GetRecipes { recipes })

        advanceUntilIdle()

        assertEquals(RecipeListUiState.Success(recipes), viewModel.uiState.value)
    }

    @Test
    fun `emits an error when loading fails`() = runTest {
        val viewModel = RecipeListViewModel(GetRecipes { throw IOException("no assets") })

        advanceUntilIdle()

        assertEquals(RecipeListUiState.Error("Couldn't load recipes"), viewModel.uiState.value)
    }
}

private val recipes = listOf(
    Recipe(
        id = "1",
        title = "Guacamole",
        description = "Chunky, lime-forward, ready in ten minutes.",
        numOfServes = 6,
        prepTimeInMins = 10,
        cookTimeInMins = 0,
        imageUrl = "",
        ingredients = listOf("3 ripe avocados", "1 lime, juiced"),
    ),
)
