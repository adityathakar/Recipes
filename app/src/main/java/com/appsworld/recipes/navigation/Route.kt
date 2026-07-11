package com.appsworld.recipes.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
data object RecipeList : NavKey

@Serializable
data class RecipeDetail(val id: String) : NavKey
