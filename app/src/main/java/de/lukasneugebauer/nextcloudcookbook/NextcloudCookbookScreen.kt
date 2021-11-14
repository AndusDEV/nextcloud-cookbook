package de.lukasneugebauer.nextcloudcookbook

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.Fastfood
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

enum class NextcloudCookbookScreen(
    val icon: ImageVector,
    val bottomBar: Boolean = false
) {
    Launch(
        icon = Icons.Filled.Home,
    ),
    Login(
        icon = Icons.Filled.Home,
    ),
    Home(
        icon = Icons.Filled.Home,
        bottomBar = true
    ),
    Categories(
        icon = Icons.Filled.Category,
        bottomBar = true
    ),
    Recipes(
        icon = Icons.Filled.Fastfood,
        bottomBar = true
    ),
    Recipe(
        icon = Icons.Filled.Fastfood
    ),
    Search(
        icon = Icons.Filled.Search
    );

    companion object {
        fun fromRoute(route: String?): NextcloudCookbookScreen =
            when (route?.substringBefore("?")) {
                Launch.name -> Launch
                Login.name -> Login
                Home.name -> Home
                Categories.name -> Categories
                Recipes.name -> Recipes
                Recipe.name -> Recipe
                Search.name -> Search
                null -> Home
                else -> throw IllegalArgumentException("Route $route is not recognized.")
            }
    }
}