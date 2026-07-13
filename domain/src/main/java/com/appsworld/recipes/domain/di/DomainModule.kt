package com.appsworld.recipes.domain.di

import com.appsworld.recipes.domain.usecase.GetRecipesSortedByTime
import com.appsworld.recipes.domain.usecase.GetRecipesSortedByTimeImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DomainModule {

    @Binds
    abstract fun bindGetRecipesSortedByTime(impl: GetRecipesSortedByTimeImpl): GetRecipesSortedByTime
}
