package com.appsworld.recipes.data.repository

import com.appsworld.recipes.domain.model.Recipe
import com.appsworld.recipes.domain.repository.GetRecipe
import com.appsworld.recipes.domain.repository.GetRecipes
import javax.inject.Inject
import javax.inject.Singleton

// Stand-in until every recipe has its own photo.
private const val PLACEHOLDER_IMAGE_URL =
    "https://www.coles.com.au/content/dam/coles/inspire-create/thumbnails/Tomato-and-bread-salad-480x288.jpg"

@Singleton
class RecipeRepositoryImpl @Inject constructor() : GetRecipes, GetRecipe {

    private val recipes = listOf(
        Recipe(
            id = "1",
            title = "Buttermilk Pancakes",
            description = "Fluffy stacks that come together in one bowl.",
            numOfServes = 4,
            prepTimeInMins = 10,
            cookTimeInMins = 15,
            imageUrl = PLACEHOLDER_IMAGE_URL,
            ingredients = listOf(
                "2 cups flour",
                "2 tbsp sugar",
                "1 tsp baking soda",
                "2 cups buttermilk",
                "2 eggs",
                "3 tbsp melted butter",
            ),
        ),
        Recipe(
            id = "2",
            title = "Spaghetti Carbonara",
            description = "Roman pasta with egg, cheese, and cured pork. No cream.",
            numOfServes = 4,
            prepTimeInMins = 10,
            cookTimeInMins = 20,
            imageUrl = PLACEHOLDER_IMAGE_URL,
            ingredients = listOf(
                "400g spaghetti",
                "150g guanciale",
                "3 egg yolks",
                "1 whole egg",
                "75g pecorino romano",
                "Black pepper",
            ),
        ),
        Recipe(
            id = "3",
            title = "Guacamole",
            description = "Chunky, lime-forward, ready in ten minutes.",
            numOfServes = 6,
            prepTimeInMins = 10,
            cookTimeInMins = 0,
            imageUrl = PLACEHOLDER_IMAGE_URL,
            ingredients = listOf(
                "3 ripe avocados",
                "1 lime, juiced",
                "1/2 red onion, diced",
                "1 jalapeno, minced",
                "Handful of cilantro",
                "Salt",
            ),
        ),
        Recipe(
            id = "4",
            title = "Chicken Curry",
            description = "A weeknight curry built on a tomato and onion base.",
            numOfServes = 6,
            prepTimeInMins = 20,
            cookTimeInMins = 45,
            imageUrl = PLACEHOLDER_IMAGE_URL,
            ingredients = listOf(
                "800g chicken thighs",
                "2 onions, sliced",
                "3 tomatoes, chopped",
                "2 tbsp ginger garlic paste",
                "2 tsp garam masala",
                "1 tsp turmeric",
                "200ml coconut milk",
            ),
        ),
        Recipe(
            id = "5",
            title = "Miso Soup",
            description = "Dashi, miso, tofu, scallions. Nothing else needed.",
            numOfServes = 4,
            prepTimeInMins = 5,
            cookTimeInMins = 10,
            imageUrl = PLACEHOLDER_IMAGE_URL,
            ingredients = listOf(
                "800ml dashi",
                "3 tbsp white miso",
                "200g silken tofu, cubed",
                "1 sheet wakame",
                "2 scallions, sliced",
            ),
        ),
    )

    override fun getRecipes(): List<Recipe> = recipes

    override fun getRecipe(id: String): Recipe? = recipes.find { it.id == id }
}
