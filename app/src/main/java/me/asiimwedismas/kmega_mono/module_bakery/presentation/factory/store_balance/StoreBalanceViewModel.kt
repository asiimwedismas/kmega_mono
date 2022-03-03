package me.asiimwedismas.kmega_mono.module_bakery.presentation.factory.store_balance

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
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.AuditRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product.GetAllProducts
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddProductFormState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class StoreBalanceViewModel @Inject constructor(
    private val auditRepository: AuditRepository,
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val allProducts: GetAllProducts,
) : ViewModel() {


    val dates: SearchDates = SearchDates()
    val productList: LiveData<List<BakeryProduct>> = allProducts()
    val addProductFormState = AddProductFormState<BakeryProduct>("Select product", emptyList())

    private var storeBalanceInvoice: BakeryInvoice = BakeryInvoice()

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    private val _itemsList = mutableStateOf<List<BakeryInvoiceItem>>(listOf())
    val itemsList: State<List<BakeryInvoiceItem>> = _itemsList

    private val _totalWholeSales = mutableStateOf(0L)
    val totalFactoryProduction: State<Long> = _totalWholeSales

    private val _totalGrossProfit = mutableStateOf<Long>(0L)
    val totalGrossProfit: State<Long> = _totalGrossProfit

    private val _totalNetProfit = mutableStateOf<Long>(0L)
    val totalNetProfit: State<Long> = _totalNetProfit

    private val _showAddFab = mutableStateOf(false)
    val showAddFab: State<Boolean> = _showAddFab

    private val _showAddItemInput = mutableStateOf(false)
    val showAddItemInput: State<Boolean> = _showAddItemInput

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

    private fun fetchSheet() {
        fetchSheetJob?.cancel()

        fetchSheetJob = viewModelScope.launch {
            Log.e("FECTH","store audit: " )
            dates.selectedDate.value?.let { date ->
                storeBalanceInvoice = auditRepository.getAuditForFactoryForDate(date)
                if (storeBalanceInvoice == BakeryInvoice()) {
                    initialiseEmptySheet()
                }
                _showAddFab.value = !storeBalanceInvoice.isLocked
                _showAddItemInput.value = !_showAddFab.value
                mutateStates()
            }
        }
    }

    private fun saveProductionSheet() {
        viewModelScope.launch {
            auditRepository.saveAudit(storeBalanceInvoice)
            mutateStates()
        }
    }

    fun deleteProductionSheet() {
        viewModelScope.launch {
            auditRepository.delete(storeBalanceInvoice.document_id)
            storeBalanceInvoice = BakeryInvoice()
            initialiseEmptySheet()
            mutateStates()
        }
    }

    fun deleteItem(item: BakeryInvoiceItem, index: Int) {
        if (storeBalanceInvoice.items[index] == item) {
            storeBalanceInvoice.items.removeAt(index)
            saveProductionSheet()
        }
    }

    private fun initialiseEmptySheet() {
        with(storeBalanceInvoice) {
            date = dates.selectedDate.value!!
            utc = dates.instance.value!!.timeInMillis
            document_author_id = ""
            document_author_name = ""
            outlet_id = "FACTORY"
            outlet_name = "FACTORY"
            type = InvoiceType.AUDIT.name
        }
    }

    private fun mutateStates() {
        _itemsList.value = storeBalanceInvoice.items
        _totalWholeSales.value = storeBalanceInvoice.totalFactorySale.toLong()
        _totalGrossProfit.value = storeBalanceInvoice.totalFactoryProfitGross.toLong()
        _totalNetProfit.value = storeBalanceInvoice.totalFactoryProfitNet.toLong()
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
            val item = BakeryInvoiceItem(selectedOption!!, qty)
            storeBalanceInvoice.items.add(item)
            saveProductionSheet()
            clearInputs()
        }
    }
}