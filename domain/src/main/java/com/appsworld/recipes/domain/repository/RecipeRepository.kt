package com.appsworld.recipes.domain.repository

import com.appsworld.recipes.domain.model.Recipe

interface RecipeRepository {

    fun getRecipes(): List<Recipe>

    fun getRecipe(id: String): Recipe?
}
