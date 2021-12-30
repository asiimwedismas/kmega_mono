package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.lifecycle.LiveData
import me.asiimwedismas.bakery_module.data.local.data_source.ProductIngredientDao
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository

class ProductIngredientRepositoryImp(
    private val dao: ProductIngredientDao,
) : ProductIngredientRepository {

    override suspend fun insert(vararg productIngredients: BakeryProductIngredient) {
        dao.insert(*productIngredients)
    }

    override suspend fun update(vararg productIngredients: BakeryProductIngredient) {
        dao.update(*productIngredients)
    }

    override suspend fun delete(vararg productIngredients: BakeryProductIngredient) {
        dao.delete(*productIngredients)
    }

    override fun getAllProductIngredients(): LiveData<List<BakeryProductIngredient>> {
        return dao.getAllProductIngredients()
    }

    override fun getRowsWithIngredient(ingredient: String): LiveData<List<BakeryProductIngredient>> {
        return dao.getRowsWithIngredient(ingredient)
    }

    override fun getIngredientsForProduct(product_name: String): LiveData<List<BakeryProductIngredient>> {
        return dao.getIngredientsForProduct(product_name)
    }
}