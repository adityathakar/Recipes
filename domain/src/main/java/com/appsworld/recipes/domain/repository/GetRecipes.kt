package com.appsworld.recipes.domain.repository

import com.appsworld.recipes.domain.model.Recipe

fun interface GetRecipes {

    suspend fun getRecipes(): List<Recipe>
}
