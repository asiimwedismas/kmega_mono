package me.asiimwedismas.kmega_mono.module_bakery.presentation.product

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product.GetAllProducts
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val allProducts: GetAllProducts,
) : ViewModel() {

    val productsList: LiveData<List<BakeryProduct>>
        get() = allProducts()
}