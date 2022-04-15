package me.asiimwedismas.kmega_mono.module_bakery.presentation.finance

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.common.SearchDates
import me.asiimwedismas.kmega_mono.common.getNextDate
import me.asiimwedismas.kmega_mono.common.getPreviousDate
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionItem
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionsSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.TransactionCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.totalTransactions
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.BakeryDatedFinances
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.GetDatedFinances
import me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components.AddSafeTransactionFormState
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FinanceViewModel @Inject constructor(
    private val financesRepository: FinancesRepository,
    private val getDatedFinance: GetDatedFinances,
) : ViewModel() {

    val dates: SearchDates = SearchDates()

    private val initBakeryDatedFinance =
        BakeryDatedFinances(
            0L,
            0L,
            0L,
            emptyList(),
            SafeTransactionsSheet()
        )
    private var datedFinances: BakeryDatedFinances = initBakeryDatedFinance

    private var transactionsSheet: SafeTransactionsSheet = SafeTransactionsSheet()

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    private val _showAddFab = mutableStateOf(false)
    val showAddFab: State<Boolean> = _showAddFab

    private val _showAddItemInput = mutableStateOf(false)
    val showAddItemInput: State<Boolean> = _showAddItemInput

    private val _editStatus = mutableStateOf(false)
    val editStatus: State<Boolean> = _editStatus

    private val _transactionsList = mutableStateOf<List<SafeTransactionItem>>(listOf())
    val transactionsList: State<List<SafeTransactionItem>> = _transactionsList

    private val _collectionsList = mutableStateOf<List<String>>(mutableListOf())
    val collectionsList: State<List<String>> = _collectionsList

    private val _showCollectionsList = mutableStateOf<Boolean>(false)
    val showCollectionsList: State<Boolean> = _showCollectionsList

    private val _totalTransactions = mutableStateOf(0L)
    val totalTransactions: State<Long> = _totalTransactions

    private val _totalCollections = mutableStateOf(0L)
    val totalCollections: State<Long> = _totalCollections

    private val _unaccountedFor = mutableStateOf(0L)
    val unaccountedFor: State<Long> = _unaccountedFor

    private val _previousDayGrossProfit = mutableStateOf(0L)
    val previousDayGrossProfit: State<Long> = _previousDayGrossProfit

    private val _previousDayNetProfit = mutableStateOf(0L)
    val previousDayNetProfit: State<Long> = _previousDayNetProfit

    private val _previousDayFlour = mutableStateOf<Long>(0L)
    val previousDayFlour: State<Long> = _previousDayFlour

    private val _isLoadingData = mutableStateOf<Boolean>(true)
    val isLoadingData: State<Boolean> = _isLoadingData

    val addTransactionFormState = AddSafeTransactionFormState("Select category")

    var fetchSheetJob: Job? = null

    init {
        fetchFinances()
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

    fun toggleShowCollectionsList() {
        _showCollectionsList.value = !_showCollectionsList.value
    }

    fun changeDate(calendar: Calendar) {
        dates.instance = MutableLiveData(calendar)
        fetchFinances()
    }

    private fun fetchFinances() {
        fetchSheetJob?.cancel()
        fetchSheetJob = viewModelScope.launch {
            dates.selectedDate.value?.let { date ->
                _isLoadingData.value = true
                datedFinances = getDatedFinance(date, dates.previousDate.value!!)
                transactionsSheet = datedFinances.safeTransactionsSheet
                if (transactionsSheet == SafeTransactionsSheet()) {
                    initialiseEmptySheet()
                }
                _showAddFab.value = !transactionsSheet.lock_status
                _showAddItemInput.value = !_showAddFab.value
                mutateStates()
            }
        }
    }

    private fun mutateStates() {
        _editStatus.value = !transactionsSheet.lock_status
        _transactionsList.value = transactionsSheet.items
        _totalTransactions.value = transactionsSheet.totalTransactions.toLong()
        _totalCollections.value = datedFinances.collections
        _collectionsList.value = datedFinances.collectionsList
        _previousDayFlour.value = datedFinances.previousDayFlour
        _previousDayGrossProfit.value = datedFinances.previousDayGrossProfit
        _unaccountedFor.value = totalCollections.value - totalTransactions.value

        val totalOfNonIngredientSpending = transactionsList.value.sumOf {
            if (!it.isIngredient) it.amount else 0
        }
        _previousDayNetProfit.value = previousDayGrossProfit.value - totalOfNonIngredientSpending
        _isLoadingData.value = false
    }

    private fun initialiseEmptySheet() {
        with(transactionsSheet) {
            date = dates.selectedDate.value!!
            utc = dates.instance.value!!.timeInMillis
            document_id = date
            document_author_id = ""
            document_author_name = ""
        }
    }

    private fun saveSheet() {
        viewModelScope.launch {
            _isLoadingData.value = true
            financesRepository.saveSafeTransactionSheet(transactionsSheet)
            mutateStates()
        }
    }

    fun deleteSheet() {
        viewModelScope.launch {
            _isLoadingData.value = true
            financesRepository.deleteSafeTransactionSheet(transactionsSheet.document_id)
            transactionsSheet = SafeTransactionsSheet()
            initialiseEmptySheet()
            mutateStates()
        }
    }

    fun deleteItem(item: SafeTransactionItem, index: Int) {
        if (transactionsSheet.items[index] == item) {
            transactionsSheet.items.removeAt(index)
            saveSheet()
        }
    }

    fun onAddFabClick() {
        _showAddItemInput.value = true
        _showAddFab.value = false
    }

    fun onAmountChanged(input: String) {
        addTransactionFormState.onAmountChanged(input)
    }

    fun onExplanationChanged(input: String) {
        addTransactionFormState.onExplanationChanged(input)
    }

    fun onCategorySelected(category: TransactionCategory) {
        addTransactionFormState.onCategorySelected(category)
    }

    fun onQueryChanged(input: String) {
        addTransactionFormState.onQueryChanged(input)
    }

    fun onClearPredictions() {
        addTransactionFormState.clearAutoCompleteField()
    }

    fun onTogglePredictions() {
        addTransactionFormState.onTogglePredictions()
    }

    fun onAddTransaction() {
        with(addTransactionFormState) {
            if (inputExplanation.isEmpty()) {
                inputExplanation = selectedOption!!.category
            }
            val transaction = SafeTransactionItem(
                category = selectedOption!!.category,
                explanation = inputExplanation,
                isIngredient = selectedOption!!.isIngredient,
                amount = amount!!
            )
            transactionsSheet.items.add(transaction)
            saveSheet()
            clearInputs()
        }
    }
}