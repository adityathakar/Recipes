package com.appsworld.recipes.domain.model

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val numOfServes: Int,
    val prepTimeInMins: Int,
    val cookTimeInMins: Int,
    val imageUrl: String,
    val ingredients: List<String>,
)
