package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.common.SearchDates
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.common.getNextDate
import me.asiimwedismas.kmega_mono.common.getPreviousDate
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.AuditRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ExpiredRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product.GetAllProducts
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.damages.DamagesViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production.ProductionViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.store_balance.StoreBalanceViewModel
import me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.used_ingredients.UsedIngredientsViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FactoryViewModel  @Inject constructor(
) : ViewModel() {

    val dates: SearchDates = SearchDates()
    lateinit var productionViewModel: ProductionViewModel
    lateinit var storeBalanceViewModel: StoreBalanceViewModel
    lateinit var damagesViewModel: DamagesViewModel
    lateinit var usedIngredientsViewModel: UsedIngredientsViewModel

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    fun selectPreviousDate() {
        changeDate(dates.instance.value!!.getPreviousDate())
    }

    fun selectNextDate() {
        changeDate(dates.instance.value!!.getNextDate())
    }

    fun toggleShowCalendar() {
        _showCalendar.value = !_showCalendar.value
    }

    fun changeDate(calendar: Calendar) {
        dates.instance = MutableLiveData(calendar)
        productionViewModel.changeDate(calendar)
        storeBalanceViewModel.changeDate(calendar)
        damagesViewModel.changeDate(calendar)
        usedIngredientsViewModel.changeDate(calendar)
    }

}