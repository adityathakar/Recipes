package com.appsworld.recipes.domain.usecase

import com.appsworld.recipes.domain.model.Recipe

fun interface GetRecipes {

    suspend fun getRecipes(): List<Recipe>
}
