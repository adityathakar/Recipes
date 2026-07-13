package com.appsworld.recipes.data.model

import kotlinx.serialization.json.Json
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class RecipeDataTest {

    private val json = Json { ignoreUnknownKeys = true }

    @Test
    fun `parses a recipe that omits the thumbnail`() {
        val recipe = json.decodeFromString<RecipeData>(recipeJson(thumbnail = null))

        assertNull(recipe.imageUrl)
        assertEquals("A title", recipe.title)
    }

    @Test
    fun `parses a recipe with an explicitly null thumbnail`() {
        val recipe = json.decodeFromString<RecipeData>(recipeJson(thumbnail = "null"))

        assertNull(recipe.imageUrl)
    }

    @Test
    fun `parses a recipe with a thumbnail`() {
        val recipe = json.decodeFromString<RecipeData>(
            recipeJson(thumbnail = "\"/content/dam/coles/thumbnail.jpg\""),
        )

        assertEquals("/content/dam/coles/thumbnail.jpg", recipe.imageUrl)
    }

    private fun recipeJson(thumbnail: String?) = """
        {
          "dynamicTitle": "A title",
          "dynamicDescription": "A description",
          ${thumbnail?.let { "\"dynamicThumbnail\": $it," } ?: ""}
          "recipeDetails": {
            "amountNumber": 4,
            "prepTimeAsMinutes": 10,
            "cookTimeAsMinutes": 20
          },
          "ingredients": [{ "ingredient": "2 eggs" }]
        }
    """.trimIndent()
}
