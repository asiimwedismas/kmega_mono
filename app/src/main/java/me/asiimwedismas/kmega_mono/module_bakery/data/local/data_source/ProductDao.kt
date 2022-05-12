package me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source

import androidx.lifecycle.LiveData
import androidx.room.*
import androidx.room.OnConflictStrategy.IGNORE
import androidx.room.OnConflictStrategy.REPLACE
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct

@Dao
interface ProductDao {

    @Insert(onConflict = REPLACE)
    suspend fun insert(products: List<BakeryProduct>)

    @Update
    suspend fun update(products: List<BakeryProduct>)

    @Delete
    suspend fun delete(products: List<BakeryProduct>)

    @Query("SELECT * FROM bakery_products WHERE product == :product")
    suspend fun getProduct(product: String): BakeryProduct

    @Query("SELECT * FROM bakery_products ORDER BY product")
    fun getAllProducts(): LiveData<List<BakeryProduct>>
}