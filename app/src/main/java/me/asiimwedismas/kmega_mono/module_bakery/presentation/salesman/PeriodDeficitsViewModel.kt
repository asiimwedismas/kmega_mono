package me.asiimwedismas.kmega_mono.module_bakery.presentation.salesman

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.core.util.Pair
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.common.SearchDates
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.CashierStanding
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository
import java.text.DateFormat
import java.util.*
import javax.inject.Inject
import kotlin.math.abs

data class SalesmanDeficitReport(
    var name: String,
    var debit_sales: Int,
    var debit_paid: Int,
    var shortages: Int,
    var excesses: Int,
    var absoluteShortageExcess: Int
)

@HiltViewModel
class PeriodDeficitsViewModel @Inject constructor(
    private val financesRepository: FinancesRepository,
) : ViewModel() {

    var dates = SearchDates()
    private val nowTimeInMillis = dates.instance.value!!.timeInMillis
    private var from: Long = nowTimeInMillis
    private var to: Long = nowTimeInMillis

    private var makeReportJob: Job? = null

    private val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US)

    private val _searchRange = mutableStateOf<String>("Select period")
    val searchRange: State<String> = _searchRange

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    private val _makingReport = mutableStateOf(true)
    val makingReport: State<Boolean> = _makingReport

    private val _reportList = mutableStateOf<List<SalesmanDeficitReport>>(emptyList())
    val reportList: State<List<SalesmanDeficitReport>> = _reportList

    fun toggleShowCalendar() {
        _showCalendar.value = !_showCalendar.value
    }

    fun handleRangeSelected(selectionPair: Pair<Long, Long>): Unit {
        from = selectionPair.first
        to = selectionPair.second

        val first = Calendar.getInstance()
        first.timeInMillis = from

        val second = Calendar.getInstance()
        second.timeInMillis = to

        _searchRange.value =
            dateFormat.format(first.time) + " - " + dateFormat.format(second.timeInMillis)

        makeReport()
    }

    fun makeReport() {
        makeReportJob?.cancel()
        makeReportJob = viewModelScope.launch {
            _makingReport.value = true
            _reportList.value = emptyList()
            mutateStates(
                financesRepository.getSalesmenHandoversInRange(from, to)
            )

        }
    }

    private fun mutateStates(salesmenHandoversInRange: List<CashierStanding>) {
        val handoversTree: MutableMap<String, SalesmanDeficitReport> = TreeMap()

        salesmenHandoversInRange.forEach { handover ->
            val salesman = handover.document_author_name!!
            if (handoversTree.containsKey(salesman)) {
                handoversTree[salesman]?.apply {
                    debit_sales += handover.debit_sales_ed
                    debit_paid += handover.debits_paid_ed

                    if (handover.variance_ed > 0) {
                        excesses += handover.variance_ed
                    } else {
                        shortages += handover.variance_ed
                    }
                }
            } else {
                val newItem = SalesmanDeficitReport(
                    name = salesman,
                    debit_sales = handover.debit_sales_ed,
                    debit_paid = handover.debits_paid_ed,
                    excesses = if (handover.variance_ed > 0) handover.variance_ed else 0,
                    shortages = if (handover.variance_ed < 0) handover.variance_ed else 0,
                    absoluteShortageExcess = 0
                )
                handoversTree.putIfAbsent(salesman, newItem)
            }
        }

        _reportList.value = handoversTree.entries.map {
            with(it.value) {
                absoluteShortageExcess =
                    abs(debit_paid) + abs(excesses) - abs(debit_sales) - abs(shortages)
            }
            it.value
        }
        _makingReport.value = false
    }
}

