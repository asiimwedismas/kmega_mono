package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository

data class ProductIngredientUseCases(
    val insert: Insert,
    val update: Update,
    val delete: Delete,
    val getAllProductIngredients: GetAllProductIngredients,
    val getRowsWithIngredient: GetRowsWithIngredient,
    val getIngredientsForProduct: GetIngredientsForProduct
)

class Insert(
    private val repository: ProductIngredientRepository,
) {
    suspend operator fun invoke(vararg productIngredients: BakeryProductIngredient) {
        repository.insert(*productIngredients)
    }
}

class Update(
    private val repository: ProductIngredientRepository,
) {
    suspend operator fun invoke(vararg productIngredients: BakeryProductIngredient) {
        repository.update(*productIngredients)
    }
}

class Delete(
    private val repository: ProductIngredientRepository,
) {
    suspend operator fun invoke(vararg productIngredients: BakeryProductIngredient) {
        repository.delete(*productIngredients)
    }
}

class GetAllProductIngredients(
    private val repository: ProductIngredientRepository,
) {
    operator fun invoke(): LiveData<List<BakeryProductIngredient>> {
        return repository.getAllProductIngredients()
    }
}

class GetRowsWithIngredient(
    private val repository: ProductIngredientRepository,
) {
    operator fun invoke(ingredient: String): LiveData<List<BakeryProductIngredient>> {
        return repository.getRowsWithIngredient(ingredient)
    }
}

class GetIngredientsForProduct(
    private val repository: ProductIngredientRepository,
) {
    operator fun invoke(productName: String): LiveData<List<BakeryProductIngredient>> {
        return repository.getIngredientsForProduct(productName)
    }
}