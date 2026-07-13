package com.appsworld.recipes.domain.usecase

import com.appsworld.recipes.domain.model.Recipe

fun interface GetRecipesSortedByTime {

    suspend fun getRecipesSortedByTime(): List<Recipe>
}
