package me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.asiimwedismas.bakery_module.domain.model.BakeryIngredient

@Dao
interface IngredientDao {

    @Insert
    suspend fun insert(vararg ingredients: BakeryIngredient)

    @Update
    suspend fun update(vararg ingredients: BakeryIngredient)

    @Delete
    suspend fun delete(vararg ingredients: BakeryIngredient)

    @Query("SELECT * FROM bakery_ingredients ORDER BY ingredient_name")
    fun getAllIngredients(): LiveData<List<BakeryIngredient>>
}