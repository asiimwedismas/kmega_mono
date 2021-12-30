package me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import kotlinx.coroutines.flow.Flow
import me.asiimwedismas.bakery_module.domain.model.BakeryProduct
import java.util.concurrent.ExecutionException

@Dao
interface ProductDao {

    @Insert(onConflict = IGNORE)
    suspend fun insert(vararg products: BakeryProduct)

    @Update
    suspend fun update(vararg products: BakeryProduct)

    @Delete
    suspend fun delete(vararg products: BakeryProduct)

    @Query("SELECT * FROM bakery_products ORDER BY product")
    fun getAllProducts(): LiveData<List<BakeryProduct>>

    @Query("SELECT * FROM bakery_products WHERE product == :product")
    fun getProduct(product: String): LiveData<BakeryProduct>
}