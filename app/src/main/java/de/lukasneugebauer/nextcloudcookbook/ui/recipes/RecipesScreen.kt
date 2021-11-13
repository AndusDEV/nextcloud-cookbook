package de.lukasneugebauer.nextcloudcookbook.ui.recipes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import de.lukasneugebauer.nextcloudcookbook.NextcloudCookbookScreen
import de.lukasneugebauer.nextcloudcookbook.R
import de.lukasneugebauer.nextcloudcookbook.ui.components.CommonListItem
import de.lukasneugebauer.nextcloudcookbook.ui.components.Loader
import de.lukasneugebauer.nextcloudcookbook.ui.theme.NcBlue

@ExperimentalMaterialApi
@Composable
fun RecipesScreen(
    navController: NavHostController,
    viewModel: RecipesViewModel = hiltViewModel()
) {
    val state = viewModel.state.value

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Recipes") },
                backgroundColor = NcBlue,
                contentColor = Color.White
            )
        }
    ) {
        if (state.data.isEmpty()) {
            Loader()
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    horizontal = dimensionResource(id = R.dimen.padding_m),
                    vertical = dimensionResource(id = R.dimen.padding_s)
                ),
                verticalArrangement = Arrangement.spacedBy(dimensionResource(id = R.dimen.padding_s))
            ) {
                items(state.data) {
                    CommonListItem(name = it.name, imageUrl = it.imageUrl) {
                        navController.navigate("${NextcloudCookbookScreen.Recipe.name}/${it.id}")
                    }
                }
            }
        }
    }
}