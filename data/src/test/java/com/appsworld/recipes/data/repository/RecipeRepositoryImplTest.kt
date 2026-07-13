package com.appsworld.recipes.data.repository

import com.appsworld.recipes.data.datastore.RecipeDataStore
import com.appsworld.recipes.data.model.IngredientData
import com.appsworld.recipes.data.model.RecipeData
import com.appsworld.recipes.data.model.RecipeDetailsData
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RecipeRepositoryImplTest {

    private val dataStore = RecipeDataStore {
        listOf(recipeData(title = "First"), recipeData(title = "Second"))
    }
    private val repository = RecipeRepositoryImpl(dataStore)

    @Test
    fun `gives each recipe its index as an id`() = runTest {
        assertEquals(listOf("0", "1"), repository.getRecipes().map { it.id })
    }

    @Test
    fun `gives a recipe the same id on every load`() = runTest {
        assertEquals(repository.getRecipes().map { it.id }, repository.getRecipes().map { it.id })
    }

    @Test
    fun `maps the recipe onto the domain model`() = runTest {
        val recipe = repository.getRecipes().first()

        assertEquals("First", recipe.title)
        assertEquals("A description", recipe.description)
        assertEquals(4, recipe.numOfServes)
        assertEquals(10, recipe.prepTimeInMins)
        assertEquals(20, recipe.cookTimeInMins)
    }

    @Test
    fun `flattens the ingredients into strings`() = runTest {
        assertEquals(listOf("2 eggs", "1 tsp salt"), repository.getRecipes().first().ingredients)
    }

    @Test
    fun `turns the thumbnail path into an absolute url`() = runTest {
        assertEquals(
            "https://www.coles.com.au/content/dam/coles/thumbnail.jpg",
            repository.getRecipes().first().imageUrl,
        )
    }

    @Test
    fun `leaves the url unset when there is no thumbnail`() = runTest {
        val dataStore = RecipeDataStore { listOf(recipeData(title = "First", imageUrl = null)) }

        assertNull(RecipeRepositoryImpl(dataStore).getRecipes().first().imageUrl)
    }

    @Test
    fun `finds the recipe with the given id`() = runTest {
        assertEquals("Second", repository.getRecipe("1")?.title)
    }

    @Test
    fun `returns nothing for an unknown id`() = runTest {
        assertNull(repository.getRecipe("nope"))
    }

    private fun recipeData(
        title: String,
        imageUrl: String? = "/content/dam/coles/thumbnail.jpg",
    ) = RecipeData(
        title = title,
        description = "A description",
        imageUrl = imageUrl,
        details = RecipeDetailsData(numOfServes = 4, prepTimeInMins = 10, cookTimeInMins = 20),
        ingredients = listOf(IngredientData("2 eggs"), IngredientData("1 tsp salt")),
    )
}
