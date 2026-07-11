package com.appsworld.recipes.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.appsworld.recipes.data.Recipe

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailViewModel,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            val recipe = uiState.recipe
            if (recipe == null) {
                Text("Recipe not found")
            } else {
                RecipeDetail(recipe)
            }
        }
    }
}

@Composable
private fun RecipeDetail(recipe: Recipe) {
    Text(
        text = recipe.title,
        style = MaterialTheme.typography.headlineMedium,
    )
    Text(
        text = recipe.description,
        style = MaterialTheme.typography.bodyMedium,
    )
    Text(
        text = "Ingredients",
        style = MaterialTheme.typography.titleMedium,
    )
    recipe.ingredients.forEach { ingredient ->
        Text(
            text = "• $ingredient",
            style = MaterialTheme.typography.bodyMedium,
        )
    }
}
