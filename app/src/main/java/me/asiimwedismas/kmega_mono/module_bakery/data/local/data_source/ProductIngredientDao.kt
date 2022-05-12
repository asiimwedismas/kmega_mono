package me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient

@Dao
interface ProductIngredientDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(productIngredients: List<BakeryProductIngredient>)

    @Update
    suspend fun update(productIngredients: List<BakeryProductIngredient>)

    @Delete
    suspend fun delete(productIngredients: List<BakeryProductIngredient>)

    @Query("SELECT * FROM bakery_product_ingredients")
    fun getAllProductIngredients(): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE ingredient = :ingredient  ORDER BY ingredient")
    fun getProductsWithIngredient(ingredient: String): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE product_name = :product  ORDER BY ingredient")
    fun getIngredientsForProduct(product: String): LiveData<List<BakeryProductIngredient>>
}