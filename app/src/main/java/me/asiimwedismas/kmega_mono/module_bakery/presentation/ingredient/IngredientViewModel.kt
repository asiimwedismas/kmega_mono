package me.asiimwedismas.kmega_mono.module_bakery.presentation.ingredient

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.DatabaseSyncUseCase
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.GetAllIngredients
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.InsertIngredients
import javax.inject.Inject

@HiltViewModel
class IngredientViewModel @Inject constructor(
    private val allIngredients: GetAllIngredients,
    private val insertIngredients: InsertIngredients,
    private val databaseSync: DatabaseSyncUseCase
) : ViewModel() {

    val ingredientList: LiveData<List<BakeryIngredient>>
        get() = allIngredients()

    @OptIn(DelicateCoroutinesApi::class)
    fun onSycnDB() = GlobalScope.launch(Dispatchers.IO) {
        databaseSync()
    }
}