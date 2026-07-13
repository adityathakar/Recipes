package com.appsworld.recipes.data.repository

import com.appsworld.recipes.data.datastore.RecipeDataStore
import com.appsworld.recipes.data.mapper.toRecipes
import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.usecase.GetRecipe
import com.appsworld.recipes.domain.usecase.GetRecipes
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val recipeDataStore: RecipeDataStore,
) : GetRecipes, GetRecipe {

    override suspend fun getRecipes(): List<Recipe> = recipeDataStore.getRecipes().toRecipes()

    override suspend fun getRecipe(id: String): Recipe? =
        recipeDataStore.getRecipes().toRecipes().find { it.id == id }
}
