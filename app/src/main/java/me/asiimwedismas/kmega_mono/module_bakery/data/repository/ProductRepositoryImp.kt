package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.ProductDao
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository

class ProductRepositoryImp(
    private val dao: ProductDao,
) : ProductRepository {

    override suspend fun insert(products: List<BakeryProduct>) {
        dao.insert(products)
    }

    override suspend fun update(products: List<BakeryProduct>) {
        dao.update(products)
    }

    override suspend fun delete(products: List<BakeryProduct>) {
        dao.delete(products)
    }

    override fun getAllProducts(): LiveData<List<BakeryProduct>> {
        return dao.getAllProducts()
    }

    override suspend fun getProduct(product: String): BakeryProduct {
        return dao.getProduct(product)
    }
}