package com.appsworld.recipes.data.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RecipeData(
    @SerialName("dynamicTitle") val title: String,
    @SerialName("dynamicDescription") val description: String,
    @SerialName("dynamicThumbnail") val imageUrl: String,
    @SerialName("recipeDetails") val details: RecipeDetailsData,
    val ingredients: List<IngredientData>,
)

@Serializable
data class RecipeDetailsData(
    @SerialName("amountNumber") val numOfServes: Int,
    @SerialName("prepTimeAsMinutes") val prepTimeInMins: Int,
    @SerialName("cookTimeAsMinutes") val cookTimeInMins: Int,
)

@Serializable
data class IngredientData(
    @SerialName("ingredient") val name: String,
)
