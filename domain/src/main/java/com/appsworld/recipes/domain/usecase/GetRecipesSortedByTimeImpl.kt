package com.appsworld.recipes.domain.usecase

import com.appsworld.recipes.domain.model.Recipe
import javax.inject.Inject

class GetRecipesSortedByTimeImpl @Inject constructor(
    private val getRecipes: GetRecipes,
) : GetRecipesSortedByTime {

    override suspend fun getRecipesSortedByTime(): List<Recipe> =
        getRecipes.getRecipes().sortedBy { it.prepTimeInMins + it.cookTimeInMins }
}
