package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product_ingredient

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetIngredientsForProduct @Inject constructor(
    private val repository: ProductIngredientRepository,
) {
    operator fun invoke(
        productName: String,
    ): LiveData<List<BakeryProductIngredient>> {
        return repository.getIngredientsForProduct(productName)
    }
}