package com.appsworld.recipes.domain.usecase

import com.appsworld.recipes.domain.model.Recipe
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test

class GetRecipesSortedByTimeImplTest {

    @Test
    fun `sorts the recipes by prep and cook time combined`() = runTest {
        val getRecipes = GetRecipes {
            listOf(
                recipe(id = "slow", prepTimeInMins = 5, cookTimeInMins = 40),
                recipe(id = "quick", prepTimeInMins = 10, cookTimeInMins = 5),
                recipe(id = "middling", prepTimeInMins = 30, cookTimeInMins = 5),
            )
        }

        val sorted = GetRecipesSortedByTimeImpl(getRecipes).getRecipesSortedByTime()

        assertEquals(listOf("quick", "middling", "slow"), sorted.map { it.id })
    }

    @Test
    fun `keeps the original order for recipes that take the same time`() = runTest {
        val getRecipes = GetRecipes {
            listOf(
                recipe(id = "first", prepTimeInMins = 10, cookTimeInMins = 20),
                recipe(id = "second", prepTimeInMins = 20, cookTimeInMins = 10),
            )
        }

        val sorted = GetRecipesSortedByTimeImpl(getRecipes).getRecipesSortedByTime()

        assertEquals(listOf("first", "second"), sorted.map { it.id })
    }

    @Test
    fun `returns nothing when there are no recipes`() = runTest {
        val getRecipes = GetRecipes { emptyList() }

        assertEquals(emptyList<Recipe>(), GetRecipesSortedByTimeImpl(getRecipes).getRecipesSortedByTime())
    }

    private fun recipe(id: String, prepTimeInMins: Int, cookTimeInMins: Int) = Recipe(
        id = id,
        title = "A recipe",
        description = "A description",
        numOfServes = 4,
        prepTimeInMins = prepTimeInMins,
        cookTimeInMins = cookTimeInMins,
        imageUrl = "/content/dam/coles/thumbnail.jpg",
        ingredients = listOf("2 eggs", "1 tsp salt"),
    )
}
