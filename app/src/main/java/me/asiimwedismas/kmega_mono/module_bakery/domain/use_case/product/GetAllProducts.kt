package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product

import androidx.lifecycle.LiveData
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetAllProducts @Inject constructor(
    private val repository: ProductRepository,
) {
    operator fun invoke(): LiveData<List<BakeryProduct>> {
        return repository.getAllProducts()
    }
}