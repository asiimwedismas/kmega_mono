package me.asiimwedismas.bakery_module.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.asiimwedismas.bakery_module.domain.model.BakeryIngredient

@Dao
interface IngredientDao {

    @Insert
    suspend fun insert(ingredient: BakeryIngredient)

    @Insert
    suspend fun insert(vararg ingredient: BakeryIngredient)

    @Update
    suspend fun update(ingredient: BakeryIngredient)

    @Delete
    suspend fun delete(ingredient: BakeryIngredient)

    @Query("SELECT * FROM bakery_ingredients ORDER BY ingredient_name")
    fun getAllIngredients(): LiveData<List<BakeryIngredient>>
}