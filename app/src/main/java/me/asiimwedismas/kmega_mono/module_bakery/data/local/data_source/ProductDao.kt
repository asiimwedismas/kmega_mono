package me.asiimwedismas.bakery_module.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import me.asiimwedismas.bakery_module.domain.model.BakeryProduct
import java.util.concurrent.ExecutionException

@Dao
interface ProductDao {

    @Insert
    suspend fun insert(product: BakeryProduct)

    @Insert
    suspend fun insert(vararg product: BakeryProduct)

    @Update
    suspend fun update(product: BakeryProduct)

    @Delete
    suspend fun delete(product: BakeryProduct)

    @Query("SELECT * FROM bakery_products ORDER BY product")
    fun getAllProducts(): LiveData<List<BakeryProduct>>

    @Query("SELECT * FROM bakery_products WHERE product == :product")
    fun getProduct(product: String): LiveData<BakeryProduct>
}