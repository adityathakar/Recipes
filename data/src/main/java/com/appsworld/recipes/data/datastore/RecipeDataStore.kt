package com.appsworld.recipes.data.datastore

import com.appsworld.recipes.data.model.RecipeData

fun interface RecipeDataStore {
    suspend fun getRecipes(): List<RecipeData>
}
