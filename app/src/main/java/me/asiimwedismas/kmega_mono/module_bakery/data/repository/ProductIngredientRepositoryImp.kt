package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.ProductIngredientDao
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository

class ProductIngredientRepositoryImp(
    private val dao: ProductIngredientDao,
) : ProductIngredientRepository {

    override suspend fun insert(productIngredients: List<BakeryProductIngredient>) {
        dao.insert(productIngredients)
    }

    override suspend fun update(productIngredients: List<BakeryProductIngredient>) {
        dao.update(productIngredients)
    }

    override suspend fun delete(productIngredients: List<BakeryProductIngredient>) {
        dao.delete(productIngredients)
    }

    override fun getAllProductIngredients(): LiveData<List<BakeryProductIngredient>> {
        return dao.getAllProductIngredients()
    }

    override fun getProductsWithIngredient(ingredient: String): LiveData<List<BakeryProductIngredient>> {
        return dao.getProductsWithIngredient(ingredient)
    }

    override fun getIngredientsForProduct(product: String): LiveData<List<BakeryProductIngredient>> {
        return dao.getIngredientsForProduct(product)
    }
}