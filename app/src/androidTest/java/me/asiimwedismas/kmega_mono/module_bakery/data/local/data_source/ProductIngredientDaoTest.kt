package me.asiimwedismas.bakery_module.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import me.asiimwedismas.bakery_module.domain.model.BakeryProductIngredient
import java.util.concurrent.ExecutionException
//
//class ProductIngredientDaoTest {
//
//    suspend fun insert(productIngredient: BakeryProductIngredient)
//
//    suspend fun insert(vararg productIngredient: BakeryProductIngredient)
//
//    suspend fun update(productIngredient: BakeryProductIngredient)
//
//    suspend fun delete(productIngredient: BakeryProductIngredient)
//
//    fun getAllProductIngredients(): Flow<List<BakeryProductIngredient>>
//
//    fun getProductIngredients(product_name: String): Flow<List<BakeryProductIngredient>>
//
//    fun getRowsWithIngredient(ingredient: String): Flow<List<BakeryProductIngredient>>
//
//    fun getIngredientsForProduct(product_name: String): Flow<List<BakeryProductIngredient>>
//}