package com.appsworld.recipes.data.datastore

import android.content.Context
import com.appsworld.recipes.data.model.RecipeData
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream

private const val RECIPES_ASSET = "recipesSample.json"

@Serializable
private data class RecipesResponse(val recipes: List<RecipeData>)

class RecipeDataStoreImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
) : RecipeDataStore {

    private val json = Json { ignoreUnknownKeys = true }

    @OptIn(ExperimentalSerializationApi::class)
    override suspend fun getRecipes(): List<RecipeData> = withContext(Dispatchers.IO) {
        context.assets.open(RECIPES_ASSET).use { input ->
            json.decodeFromStream<RecipesResponse>(input).recipes
        }
    }
}
