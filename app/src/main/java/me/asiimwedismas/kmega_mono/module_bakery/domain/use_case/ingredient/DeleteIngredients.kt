package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DeleteIngredients @Inject constructor(
    private val repository: IngredientRepository,
) {
    suspend operator fun invoke(
        ingredients: List<BakeryIngredient>,
    ) {
        repository.delete(ingredients)
    }
}