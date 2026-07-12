package com.appsworld.recipes.data

data class Recipe(
    val id: String,
    val title: String,
    val description: String,
    val numOfServes: Int,
    val prepTime: String,
    val cookTime: String,
    val imageUrl: String,
    val ingredients: List<String>,
)
