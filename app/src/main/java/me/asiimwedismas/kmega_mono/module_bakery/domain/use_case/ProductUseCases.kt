package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import androidx.lifecycle.LiveData
import me.asiimwedismas.bakery_module.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository

data class ProductUseCases(
    val insertProducts: InsertProducts,
    val updateProducts: UpdateProducts,
    val deleteProducts: DeleteProducts,
    val getAllProducts: GetAllProducts,
    val getProduct: GetProduct,
)


class InsertProducts(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(vararg products: BakeryProduct) {
        repository.insert(*products)
    }
}

class UpdateProducts(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(vararg products: BakeryProduct) {
        repository.update(*products)
    }
}

class DeleteProducts(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(vararg products: BakeryProduct) {
        repository.delete(*products)
    }
}

class GetAllProducts(
    private val repository: ProductRepository,
) {
    operator fun invoke(): LiveData<List<BakeryProduct>> {
        return repository.getAllProducts()
    }
}

class GetProduct(
    private val repository: ProductRepository,
) {
    operator fun invoke(productName: String): LiveData<BakeryProduct> {
        return repository.getProduct(productName)
    }
}