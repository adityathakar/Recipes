package com.appsworld.recipes.data.di

import com.appsworld.recipes.data.repository.RecipeRepositoryImpl
import com.appsworld.recipes.domain.repository.GetRecipe
import com.appsworld.recipes.domain.repository.GetRecipes
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataModule {

    @Binds
    abstract fun bindGetRecipes(impl: RecipeRepositoryImpl): GetRecipes

    @Binds
    abstract fun bindGetRecipe(impl: RecipeRepositoryImpl): GetRecipe
}
