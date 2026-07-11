package com.appsworld.recipes.ui.list

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.dropUnlessResumed

@Composable
fun RecipeListScreen(
    modifier: Modifier = Modifier,
    onRecipeClick: (String) -> Unit,
) {
    Scaffold(modifier = modifier.fillMaxSize()) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text("Recipes")
            repeat(3) { index ->
                val id = (index + 1).toString()
                Button(onClick = dropUnlessResumed { onRecipeClick(id) }) {
                    Text("Recipe $id")
                }
            }
        }
    }
}
