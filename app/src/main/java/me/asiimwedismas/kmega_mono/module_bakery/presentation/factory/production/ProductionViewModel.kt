package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.common.SearchDates
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.common.getNextDate
import me.asiimwedismas.kmega_mono.common.getPreviousDate
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionItem
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductionViewModel @Inject constructor(
    private val productionRepository: ProductionRepository,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider
) : ViewModel() {

    val dates: SearchDates = SearchDates()

    private var productionSheet: FactoryProductionSheet = FactoryProductionSheet()

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    private val _itemsList = mutableStateOf<List<FactoryProductionItem>>(listOf())
    val itemsList: State<List<FactoryProductionItem>> = _itemsList

    private val _totalFactoryProduction = mutableStateOf(0)
    val totalFactoryProduction: State<Int> = _totalFactoryProduction

    var fetchSheetJob: Job? = null

    init {
        fetchSheet()
    }


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
        fetchSheet()
    }

    fun fetchSheet() {
        fetchSheetJob?.cancel()

        fetchSheetJob =  viewModelScope.launch(Dispatchers.IO) {
            dates.selectedDate.value?.let { date ->
                productionSheet = productionRepository.getProductionSheetForDate(date)
                _itemsList.value = productionSheet.items
                _totalFactoryProduction.value = productionSheet.items.sumOf { it.wholesale_sales }
            }
        }
    }

}