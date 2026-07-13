package com.appsworld.recipes.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import coil3.compose.AsyncImage
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.ui.theme.BrandRed
import com.appsworld.recipes.ui.theme.RecipesTheme

@Composable
fun RecipeListScreen(
    viewModel: RecipeListViewModel,
    onRecipeClick: (String) -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecipeListScreen(
        uiState = uiState,
        onRecipeClick = onRecipeClick,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeListScreen(
    modifier: Modifier = Modifier,
    uiState: RecipeListUiState,
    onRecipeClick: (String) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        topBar = { TopAppBar(title = { Text("Recipes") }) },
    ) { innerPadding ->
        when (uiState) {
            RecipeListUiState.Loading -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    CircularProgressIndicator()
                }
            }

            is RecipeListUiState.Success -> {
                LazyVerticalGrid(
                    columns = GridCells.Adaptive(minSize = 300.dp),
                    modifier = Modifier.padding(innerPadding),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(24.dp),
                ) {
                    items(uiState.recipes, key = { it.id }) { recipe ->
                        RecipeCard(
                            recipe = recipe,
                            onClick = dropUnlessResumed { onRecipeClick(recipe.id) },
                        )
                    }
                }
            }

            is RecipeListUiState.Error -> {
                Box(
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize(),
                    contentAlignment = Alignment.Center,
                ) {
                    Text(
                        text = uiState.message,
                        style = MaterialTheme.typography.bodyLarge,
                    )
                }
            }
        }
    }
}

@Composable
private fun RecipeCard(
    recipe: Recipe,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable(role = Role.Button, onClick = onClick)) {
        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(5f / 3f)
                .clip(RoundedCornerShape(4.dp)),
            placeholder = ColorPainter(Color(0xFFE8E8E8)),
            fallback = ColorPainter(Color(0xFFE8E8E8)),
            contentScale = ContentScale.Crop,
        )
        Text(
            text = "RECIPE",
            modifier = Modifier.padding(top = 12.dp),
            color = BrandRed,
            style = MaterialTheme.typography.labelMedium,
            fontWeight = FontWeight.Bold,
            letterSpacing = 1.sp,
        )
        Text(
            text = recipe.title,
            modifier = Modifier.padding(top = 4.dp),
            style = MaterialTheme.typography.titleMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private val sampleRecipes = listOf(
    Recipe(
        id = "1",
        title = "Curtis Stone's tomato and bread salad with BBQ eggplant and capsicum",
        description = "",
        numOfServes = 4,
        prepTimeInMins = 20,
        cookTimeInMins = 15,
        imageUrl = "",
        ingredients = emptyList(),
    ),
    Recipe(
        id = "2",
        title = "Pork, fennel and sage ragu with polenta",
        description = "",
        numOfServes = 8,
        prepTimeInMins = 15,
        cookTimeInMins = 270,
        imageUrl = "",
        ingredients = emptyList(),
    ),
    Recipe(
        id = "3",
        title = "Apple and kale panzanella",
        description = "",
        numOfServes = 6,
        prepTimeInMins = 15,
        cookTimeInMins = 10,
        imageUrl = "",
        ingredients = emptyList(),
    ),
    Recipe(
        id = "4",
        title = "Tasia and Gracia's miso steak tacos with kaleslaw",
        description = "",
        numOfServes = 4,
        prepTimeInMins = 25,
        cookTimeInMins = 20,
        imageUrl = "",
        ingredients = emptyList(),
    ),
)

@Preview(showBackground = true)
@Composable
private fun RecipeListScreenPreview() {
    RecipesTheme {
        RecipeListScreen(
            uiState = RecipeListUiState.Success(recipes = sampleRecipes),
            onRecipeClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeListScreenLoadingPreview() {
    RecipesTheme {
        RecipeListScreen(
            uiState = RecipeListUiState.Loading,
            onRecipeClick = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun RecipeListScreenErrorPreview() {
    RecipesTheme {
        RecipeListScreen(
            uiState = RecipeListUiState.Error(message = "Couldn't load recipes"),
            onRecipeClick = {},
        )
    }
}
