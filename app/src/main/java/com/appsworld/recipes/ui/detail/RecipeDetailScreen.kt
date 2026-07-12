package com.appsworld.recipes.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import com.appsworld.recipes.R
import com.appsworld.recipes.data.Recipe

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    viewModel: RecipeDetailViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = dropUnlessResumed { onBack() }) {
                        Icon(
                            painter = painterResource(R.drawable.ic_arrow_back),
                            contentDescription = "Back",
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
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
