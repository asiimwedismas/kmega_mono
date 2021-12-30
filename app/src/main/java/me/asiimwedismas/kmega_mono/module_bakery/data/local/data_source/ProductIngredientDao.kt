package me.asiimwedismas.bakery_module.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient

@Dao
interface ProductIngredientDao {

    @Insert(onConflict = IGNORE)
    suspend fun insert(vararg productIngredients: BakeryProductIngredient)

    @Update
    suspend fun update(vararg productIngredients: BakeryProductIngredient)

    @Delete
    suspend fun delete(vararg productIngredients: BakeryProductIngredient)

    @Query("SELECT * FROM bakery_product_ingredients")
    fun getAllProductIngredients(): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE ingredient = :ingredient  ORDER BY ingredient")
    fun getRowsWithIngredient(ingredient: String): LiveData<List<BakeryProductIngredient>>

    @Query("SELECT * FROM bakery_product_ingredients WHERE product_name = :product_name  ORDER BY ingredient")
    fun getIngredientsForProduct(product_name: String): LiveData<List<BakeryProductIngredient>>
}