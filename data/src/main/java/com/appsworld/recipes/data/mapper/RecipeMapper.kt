package com.appsworld.recipes.data.mapper

import com.appsworld.recipes.data.model.RecipeData
import com.appsworld.recipes.domain.model.Recipe

private const val IMAGE_BASE_URL = "https://www.coles.com.au"

fun List<RecipeData>.toRecipes(): List<Recipe> =
    mapIndexed { index, data -> data.toRecipe(id = index.toString()) }

private fun RecipeData.toRecipe(id: String) = Recipe(
    id = id,
    title = title,
    description = description,
    numOfServes = details.numOfServes,
    prepTimeInMins = details.prepTimeInMins,
    cookTimeInMins = details.cookTimeInMins,
    imageUrl = IMAGE_BASE_URL + imageUrl,
    ingredients = ingredients.map { it.name },
)
