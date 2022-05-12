package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UpdateProducts @Inject constructor(
    private val repository: ProductRepository,
) {
    suspend operator fun invoke(
        products: List<BakeryProduct>
    ) {
        repository.update(products)
    }
}