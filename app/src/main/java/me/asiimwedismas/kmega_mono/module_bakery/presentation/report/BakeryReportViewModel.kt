package me.asiimwedismas.kmega_mono.module_bakery.presentation.report

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import me.asiimwedismas.kmega_mono.common.SearchDates
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.common.getNextDate
import me.asiimwedismas.kmega_mono.common.getPreviousDate
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.DispatchesReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report.DatedFactoryReport
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report.DatedOutletReport
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report.DatedSalesmenReport
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BakeryReportViewModel @Inject constructor(
    private val factoryReport: DatedFactoryReport,
    private val salesmenReport: DatedSalesmenReport,
    private val outletReport: DatedOutletReport,
    private val disptachers: CoroutineDispatchersProvider,
) : ViewModel() {
    val sd = SearchDates()

    var makeReportJob: Job? = null

    private val _dispatchesReportCard = mutableStateOf(DispatchesReportCard.createEmpty())
    val dispatchesReportCard: State<DispatchesReportCard> = _dispatchesReportCard

    private val _factoryReportCard = mutableStateOf(FactoryReportCard.emptyCard())
    val factoryReportCard: State<FactoryReportCard> = _factoryReportCard

    private val _outletsReportCard =
        mutableStateOf<OutletReportCard>(OutletReportCard.createEmpty())
    val outletsReportCard: State<OutletReportCard> = _outletsReportCard

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    private val _makingReport = mutableStateOf<Boolean>(true)
    val makingReport: State<Boolean> = _makingReport

    init {
        makeReport()
    }

    fun selectPreviousDate() {
        changeDate(sd.instance.value!!.getPreviousDate())
    }

    fun selectNextDate() {
        changeDate(sd.instance.value!!.getNextDate())
    }

    fun toggleShowCalendar() {
        _showCalendar.value = !_showCalendar.value
    }

    fun changeDate(calendar: Calendar) {
        sd.instance = MutableLiveData(calendar)
        makeReport()
    }

    fun makeReport() {
        makeReportJob?.cancel()
        makeReportJob = viewModelScope.launch {
            _makingReport.value = true

            sd.selectedDate.value?.let { date ->
                _factoryReportCard.value = FactoryReportCard.emptyCard()

                val factoryDef = async { factoryReport(sd.previousDate.value!!, date) }
                val dispatchesDef = async { salesmenReport(date) }
                val outletsDef = async { outletReport(date) }

                val factReport = factoryDef.await()
                val dispatchesReport = dispatchesDef.await()
                val outletReport = outletsDef.await()

                var outletDeliveries = 0L
                with(dispatchesReport) {
                    dispatches = factReport.dispatched
                    returns = factReport.returned
                    calulate()

                    outletDeliveries = outletList.sumOf { invoice ->
                        invoice.items.sumOf { it.total_outlet_sale }
                    }.toLong()
                }

                outletReport.updateOutletDeliveries(outletDeliveries)

                _factoryReportCard.value = factReport
                _dispatchesReportCard.value = dispatchesReport
                _outletsReportCard.value = outletReport
            }
            _makingReport.value = false
        }
    }
}