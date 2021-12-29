package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.IngredientDao
import me.asiimwedismas.bakery_module.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository

class IngredientRepositoryImp(
    private val dao: IngredientDao,
) : IngredientRepository {

    override suspend fun insert(vararg ingredients: BakeryIngredient) {
        dao.insert(*ingredients)
    }

    override suspend fun update(vararg ingredients: BakeryIngredient) {
        dao.update(*ingredients)
    }

    override suspend fun delete(vararg ingredients: BakeryIngredient) {
        dao.delete(*ingredients)
    }

    override fun getAllIngredients(): LiveData<List<BakeryIngredient>> {
        return dao.getAllIngredients()
    }
}