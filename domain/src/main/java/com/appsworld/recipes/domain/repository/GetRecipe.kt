package com.appsworld.recipes.domain.repository

import com.appsworld.recipes.domain.model.Recipe

fun interface GetRecipe {

    fun getRecipe(id: String): Recipe?
}
