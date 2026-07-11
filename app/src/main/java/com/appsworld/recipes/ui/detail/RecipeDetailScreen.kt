package com.appsworld.recipes.ui.detail

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    recipeId: String,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Text(
            text = "Recipe $recipeId",
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
        )
    }
}
