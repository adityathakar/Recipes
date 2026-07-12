package com.appsworld.recipes.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.heading
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.compose.dropUnlessResumed
import coil3.compose.AsyncImage
import com.appsworld.recipes.R
import com.appsworld.recipes.data.Recipe
import com.appsworld.recipes.ui.formatDuration
import com.appsworld.recipes.ui.spokenDuration
import com.appsworld.recipes.ui.theme.RecipesTheme

@Composable
fun RecipeDetailScreen(
    viewModel: RecipeDetailViewModel,
    onBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    RecipeDetailScreen(
        uiState = uiState,
        onBack = onBack,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RecipeDetailScreen(
    modifier: Modifier = Modifier,
    uiState: RecipeDetailUiState,
    onBack: () -> Unit,
) {
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
        val recipe = uiState.recipe
        if (recipe == null) {
            Box(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = "Recipe not found",
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        } else {
            RecipeDetail(
                modifier = Modifier.padding(innerPadding),
                recipe = recipe,
            )
        }
    }
}

@Composable
private fun RecipeDetail(
    modifier: Modifier = Modifier,
    recipe: Recipe,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp, vertical = 24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Text(
            modifier = Modifier
                .semantics { heading() },
            text = recipe.title,
            style = MaterialTheme.typography.headlineLarge,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
        )

        Text(
            text = recipe.description,
            modifier = Modifier
                .padding(top = 16.dp),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center,
        )

        AsyncImage(
            model = recipe.imageUrl,
            contentDescription = null,
            modifier = Modifier
                .padding(top = 24.dp)
                .aspectRatio(5f / 3f)
                .clip(RoundedCornerShape(4.dp)),
            placeholder = ColorPainter(Color(0xFFE8E8E8)),
            contentScale = ContentScale.Crop,
        )

        HorizontalDivider(modifier = Modifier.padding(top = 24.dp))
        RecipeMetaRow(recipe = recipe)
        HorizontalDivider()

        Text(
            modifier = Modifier
                .align(Alignment.Start)
                .padding(top = 32.dp)
                .semantics { heading() },
            text = "Ingredients",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
        )
        Column(
            modifier = Modifier.padding(top = 16.dp, bottom = 32.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            recipe.ingredients.forEach { ingredient ->
                IngredientRow(ingredient = ingredient)
            }
        }
    }
}

@Composable
private fun RecipeMetaRow(
    modifier: Modifier = Modifier,
    recipe: Recipe,
) {
    Row(
        modifier = modifier.height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        MetaItem(
            label = "Serves",
            value = recipe.numOfServes.toString(),
            contentDescription = "Serves, ${recipe.numOfServes}",
        )
        VerticalDivider()
        MetaItem(
            label = "Prep",
            value = recipe.prepTimeInMins.formatDuration(),
            contentDescription = "Prep time, ${recipe.prepTimeInMins.spokenDuration()}",
        )
        VerticalDivider()
        MetaItem(
            label = "Cooking",
            value = recipe.cookTimeInMins.formatDuration(),
            contentDescription = "Cooking time, ${recipe.cookTimeInMins.spokenDuration()}",
        )
    }
}

@Composable
private fun RowScope.MetaItem(
    label: String,
    value: String,
    contentDescription: String,
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .semantics(mergeDescendants = true) { this.contentDescription = contentDescription }
            .padding(vertical = 16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp),
    ) {
        Text(
            modifier = Modifier.clearAndSetSemantics {},
            text = label,
            style = MaterialTheme.typography.labelMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            modifier = Modifier.clearAndSetSemantics {},
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
        )
    }
}

@Composable
private fun IngredientRow(
    modifier: Modifier = Modifier,
    ingredient: String,
) {
    Row(
        modifier = modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Icon(
            modifier = Modifier
                .size(18.dp),
            painter = painterResource(R.drawable.ic_chevron_right),
            contentDescription = null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Text(
            text = ingredient,
            style = MaterialTheme.typography.bodyLarge,
        )
    }
}

private val sampleRecipe = Recipe(
    id = "1",
    title = "Pork, fennel and sage ragu with polenta",
    description = "Put your slow cooker to work and make this mouth-watering pork ragu. " +
            "Served with fluffy polenta, it's a guaranteed crowd-pleaser.",
    numOfServes = 8,
    prepTimeInMins = 15,
    cookTimeInMins = 270,
    imageUrl = "",
    ingredients = listOf(
        "1.2kg Coles Australian Pork Slow Cook Scotch Roast, cut into 10cm pieces",
        "1 tsp ground fennel or fennel seeds",
        "2 tbsp olive oil",
        "1 brown onion, finely chopped",
        "700g tomato passata",
        "1/4 cup sage leaves",
    ),
)

@Preview(showBackground = true)
@Composable
private fun RecipeDetailScreenPreview() {
    RecipesTheme {
        RecipeDetailScreen(
            uiState = RecipeDetailUiState(recipe = sampleRecipe),
            onBack = {},
        )
    }
}

@Preview(showBackground = true, widthDp = 900, heightDp = 420)
@Composable
private fun RecipeDetailScreenLandscapePreview() {
    RecipesTheme {
        RecipeDetailScreen(
            uiState = RecipeDetailUiState(recipe = sampleRecipe),
            onBack = {},
        )
    }
}

@Preview(showBackground = true, fontScale = 2f)
@Composable
private fun RecipeDetailScreenLargeFontPreview() {
    RecipesTheme {
        RecipeDetailScreen(
            uiState = RecipeDetailUiState(recipe = sampleRecipe),
            onBack = {},
        )
    }
}
