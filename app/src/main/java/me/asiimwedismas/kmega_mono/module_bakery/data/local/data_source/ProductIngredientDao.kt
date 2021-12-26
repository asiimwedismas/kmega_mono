package me.asiimwedismas.bakery_module.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.asiimwedismas.bakery_module.domain.model.BakeryProductIngredient
import java.util.concurrent.ExecutionException

@Dao
interface ProductIngredientDao {

    @Insert
    suspend fun insert(productIngredient: BakeryProductIngredient)

    @Insert
    suspend fun insert(vararg productIngredient: BakeryProductIngredient)

    @Update
    suspend fun update(productIngredient: BakeryProductIngredient)

    @Delete
    suspend fun delete(productIngredient: BakeryProductIngredient)

    @Query("SELECT * FROM bakery_product_ingredients")
    fun getAllProductIngredients(): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE product_name = :product_name  ORDER BY ingredient")
    fun getProductIngredients(product_name: String): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE ingredient = :ingredient  ORDER BY ingredient")
    fun getRowsWithIngredient(ingredient: String): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE product_name = :product_name  ORDER BY ingredient")
    fun getIngredientsForProduct(product_name: String): LiveData<List<BakeryProductIngredient>>
}