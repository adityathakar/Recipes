package com.appsworld.recipes.data.datastore

import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class RecipeDataStoreImplTest {

    private val dataStore = RecipeDataStoreImpl(RuntimeEnvironment.getApplication())

    @Test
    fun `parses every recipe in the asset`() = runTest {
        assertEquals(8, dataStore.getRecipes().size)
    }

    @Test
    fun `maps the renamed top level fields`() = runTest {
        val recipe = dataStore.getRecipes().first()

        assertEquals(
            "Curtis Stone's tomato and bread salad with BBQ eggplant and capsicum",
            recipe.title,
        )
        assertEquals(
            "For a crowd-pleasing salad, try this tasty combination of fresh tomato, " +
                "crunchy bread and BBQ veggies. It’s topped with fresh basil and " +
                "oregano for a finishing touch. ",
            recipe.description,
        )
        assertEquals(
            "/content/dam/coles/inspire-create/thumbnails/Tomato-and-bread-salad-480x288.jpg",
            recipe.imageUrl,
        )
    }

    @Test
    fun `reads the nested recipe details`() = runTest {
        val details = dataStore.getRecipes().first().details

        assertEquals(8, details.numOfServes)
        assertEquals(15, details.prepTimeInMins)
        assertEquals(15, details.cookTimeInMins)
    }

    @Test
    fun `unwraps each ingredient object`() = runTest {
        val ingredients = dataStore.getRecipes().first().ingredients

        assertEquals(14, ingredients.size)
        assertEquals("1 cup (250ml) extra virgin olive oil, divided", ingredients.first().name)
    }

    @Test
    fun `parses recipes that omit the optional prep note`() = runTest {
        // prepNote is present on only 4 of the 8 recipes and RecipeData drops it entirely,
        // so this really guards ignoreUnknownKeys staying enabled.
        assertEquals(11, dataStore.getRecipes()[1].ingredients.size)
    }
}
