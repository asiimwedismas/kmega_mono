package me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient

@Dao
interface IngredientDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(ingredients: List<BakeryIngredient>)

    @Update
    suspend fun update(ingredients: List<BakeryIngredient>)

    @Delete
    suspend fun delete(ingredients: List<BakeryIngredient>)

    @Query("SELECT * FROM bakery_ingredients ORDER BY ingredient_name")
    fun getAllIngredients(): LiveData<List<BakeryIngredient>>
}