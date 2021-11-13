package de.lukasneugebauer.nextcloudcookbook.ui.recipes

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import de.lukasneugebauer.nextcloudcookbook.domain.model.RecipePreview
import de.lukasneugebauer.nextcloudcookbook.domain.repository.RecipeRepository
import de.lukasneugebauer.nextcloudcookbook.utils.Resource
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val recipeRepository: RecipeRepository
) : ViewModel() {

    private val _state: MutableState<RecipesScreenState> =
        mutableStateOf(RecipesScreenState(data = emptyList()))
    val state: State<RecipesScreenState> = _state

    init {
        viewModelScope.launch {
            when (val recipesResource = recipeRepository.getRecipes()) {
                is Resource.Success -> {
                    _state.value = RecipesScreenState(data = recipesResource.data ?: emptyList())
                }
                is Resource.Error -> TODO("recipes resource error")
            }

        }
    }
}

data class RecipesScreenState(
    val data: List<RecipePreview>
)