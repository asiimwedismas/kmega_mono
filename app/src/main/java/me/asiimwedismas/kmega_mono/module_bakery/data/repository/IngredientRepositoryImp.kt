package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.lifecycle.LiveData
import com.google.firebase.firestore.CollectionReference
import kotlinx.coroutines.tasks.await
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.IngredientDao
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository

class IngredientRepositoryImp(
    private val dao: IngredientDao
) : IngredientRepository {

    override suspend fun insert(ingredients: List<BakeryIngredient>) {
        dao.insert(ingredients)
    }

    override suspend fun update(ingredients: List<BakeryIngredient>) {
        dao.update(ingredients)
    }

    override suspend fun delete(ingredients: List<BakeryIngredient>) {
        dao.delete(ingredients)
    }

    override fun getAllIngredients(): LiveData<List<BakeryIngredient>> {
        return dao.getAllIngredients()
    }
}