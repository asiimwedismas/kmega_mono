package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product_ingredient

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateProductIngredients @Inject constructor(
    private val repository: ProductIngredientRepository,
) {
    suspend operator fun invoke(
        productIngredients: List<BakeryProductIngredient>,
    ) {
        repository.update(productIngredients)
    }
}