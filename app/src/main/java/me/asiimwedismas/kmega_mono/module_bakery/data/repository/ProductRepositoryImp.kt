package me.asiimwedismas.kmega_mono.module_bakery.data.repository

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.ProductDao
import me.asiimwedismas.bakery_module.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository

class ProductRepositoryImp(
    private val dao: ProductDao,
) : ProductRepository {

    override suspend fun insert(vararg products: BakeryProduct) {
        dao.insert(*products)
    }

    override suspend fun update(vararg products: BakeryProduct) {
        dao.update(*products)
    }

    override suspend fun delete(vararg products: BakeryProduct) {
        dao.delete(*products)
    }

    override fun getAllProducts(): LiveData<List<BakeryProduct>> {
        return dao.getAllProducts()
    }

    override fun getProduct(product: String): LiveData<BakeryProduct> {
        return dao.getProduct(product)
    }
}