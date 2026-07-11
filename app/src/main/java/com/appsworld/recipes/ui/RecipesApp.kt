package com.appsworld.recipes.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import com.appsworld.recipes.navigation.RecipeDetail
import com.appsworld.recipes.navigation.RecipeList
import com.appsworld.recipes.ui.detail.RecipeDetailScreen
import com.appsworld.recipes.ui.detail.RecipeDetailViewModel
import com.appsworld.recipes.ui.list.RecipeListScreen

@Composable
fun RecipesApp(modifier: Modifier = Modifier) {
    val backStack = rememberNavBackStack(RecipeList)

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryDecorators = listOf(
            rememberSaveableStateHolderNavEntryDecorator(),
            rememberViewModelStoreNavEntryDecorator(),
        ),
        entryProvider = entryProvider {
            entry<RecipeList> {
                RecipeListScreen(
                    viewModel = hiltViewModel(),
                    onRecipeClick = { id -> backStack.add(RecipeDetail(id)) },
                )
            }
            entry<RecipeDetail> { key ->
                RecipeDetailScreen(
                    viewModel = hiltViewModel<RecipeDetailViewModel, RecipeDetailViewModel.Factory>(
                        creationCallback = { factory -> factory.create(key.id) },
                    ),
                )
            }
        },
        modifier = modifier.fillMaxSize(),
    )
}
