package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.production

import android.util.Log
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
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.*
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product.GetAllProducts
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddProductFormState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ProductionViewModel @Inject constructor(
    private val productionRepository: ProductionRepository,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val allProducts: GetAllProducts,
) : ViewModel() {


    private val dates: SearchDates = SearchDates()
    val productList: LiveData<List<BakeryProduct>> = allProducts()
    val addProductFormState = AddProductFormState<BakeryProduct>("Select product", emptyList())

    private var productionSheet: FactoryProductionSheet = FactoryProductionSheet()

    private val _itemsList = mutableStateOf<List<FactoryProductionItem>>(listOf())
    val itemsList: State<List<FactoryProductionItem>> = _itemsList

    private val _totalFactoryProduction = mutableStateOf(0L)
    val totalFactoryProduction: State<Long> = _totalFactoryProduction

    private val _totalGrossProfit = mutableStateOf<Long>(0L)
    val totalGrossProfit: State<Long> = _totalGrossProfit

    private val _totalNetProfit = mutableStateOf<Long>(0L)
    val totalNetProfit: State<Long> = _totalNetProfit

    private val _showAddFab = mutableStateOf(false)
    val showAddFab: State<Boolean> = _showAddFab

    private val _showAddItemInput = mutableStateOf(false)
    val showAddItemInput: State<Boolean> = _showAddItemInput

    private val _editStatus = mutableStateOf<Boolean>(false)
    val editStatus: State<Boolean> = _editStatus

    private val _isLoadingData = mutableStateOf<Boolean>(true)
    val isLoadingData: State<Boolean> = _isLoadingData

    var fetchSheetJob: Job? = null

    init {
        fetchSheet()
    }

    fun changeDate(calendar: Calendar) {
        dates.instance = MutableLiveData(calendar)
        fetchSheet()
    }

    private fun fetchSheet() {
        fetchSheetJob?.cancel()

        fetchSheetJob = viewModelScope.launch {
            Log.e("FECTH","production: " )
            dates.selectedDate.value?.let { date ->
                _isLoadingData.value = true
                productionSheet = productionRepository.getProductionSheetForDate(date)
                if (productionSheet == FactoryProductionSheet()) {
                    initialiseEmptySheet()
                }
                _showAddFab.value = !productionSheet.lock_status
                _showAddItemInput.value = !_showAddFab.value
                mutateStates()
            }
        }
    }

    private fun saveProductionSheet() {
        viewModelScope.launch {
            _isLoadingData.value = true
            productionRepository.saveProductionSheet(productionSheet)
            mutateStates()
        }
    }

    fun deleteProductionSheet() {
        viewModelScope.launch {
            _isLoadingData.value = true
            productionRepository.delete(productionSheet.document_id)
            productionSheet = FactoryProductionSheet()
            initialiseEmptySheet()
            mutateStates()
        }
    }

    fun deleteItem(item: FactoryProductionItem, index: Int) {
        if (productionSheet.items[index] == item) {
            productionSheet.items.removeAt(index)
            saveProductionSheet()
        }
    }

    private fun initialiseEmptySheet() {
        with(productionSheet) {
            date = dates.selectedDate.value!!
            utc = dates.instance.value!!.timeInMillis
            document_id = date
            document_author_id = ""
            document_author_name = ""
        }
    }

    private fun mutateStates() {
        _editStatus.value = !productionSheet.lock_status
        _itemsList.value = productionSheet.items
        _totalFactoryProduction.value = productionSheet.totalWholeSales.toLong()
        _totalGrossProfit.value = productionSheet.totalGrossProfit.toLong()
        _totalNetProfit.value = productionSheet.totalNetProfit.toLong()
        _isLoadingData.value = false
    }

    fun onAddFabClick() {
        _showAddItemInput.value = true
        _showAddFab.value = false
    }

    fun onQueryChanged(newQuery: String) {
        addProductFormState.onQueryChanged(newQuery) { item, query ->
            item.product.contains(query, true)
        }
    }

    fun onAddItem() {
        with(addProductFormState) {
            val item = FactoryProductionItem(selectedOption!!, qty.toInt())
            productionSheet.items.add(item)
            saveProductionSheet()
            clearInputs()
        }
    }
}