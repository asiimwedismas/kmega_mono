package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.GetAllIngredients
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.InsertIngredients
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val allIngredients: GetAllIngredients,
    private val insertIngredients: InsertIngredients,
) : ViewModel() {

    val ingredientList: LiveData<List<BakeryIngredient>>
        get() = allIngredients()
}