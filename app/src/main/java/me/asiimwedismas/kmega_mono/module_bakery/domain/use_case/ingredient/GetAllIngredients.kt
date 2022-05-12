package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllIngredients @Inject constructor(
    private val repository: IngredientRepository,
) {
    operator fun invoke(): LiveData<List<BakeryIngredient>> {
        return repository.getAllIngredients()
    }
}