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
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.*
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

    private val initDispatchesCard = DispatchesReportCard.createEmpty()
    private val _dispatchesReportCard = mutableStateOf(initDispatchesCard)
    val dispatchesReportCard: State<DispatchesReportCard> = _dispatchesReportCard

    private val initFactoryReportCard = FactoryReportCard.emptyCard()
    private val _factoryReportCard = mutableStateOf(initFactoryReportCard)
    val factoryReportCard: State<FactoryReportCard> = _factoryReportCard

    private val initOutletReportCard = OutletReportCard.createEmpty()
    private val _outletsReportCard = mutableStateOf(initOutletReportCard)
    val outletsReportCard: State<OutletReportCard> = _outletsReportCard

    private val initBreakDown = DispatchedBreakDown(FactoryReportCard.emptyCard())
    private val _dispatchedBreakdownReportCard = mutableStateOf(initBreakDown)
    val dispatchedBreakDownReportCard: State<DispatchedBreakDown> = _dispatchedBreakdownReportCard

    private val initMoneysReportCard = MoneysReportCard(initDispatchesCard, initOutletReportCard)
    private val _moneysReportCard = mutableStateOf(initMoneysReportCard)
    val moneysReportCard: State<MoneysReportCard> = _moneysReportCard


    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

    private val _makingReport = mutableStateOf(true)
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
                _dispatchedBreakdownReportCard.value = factReport.dispatchedBreakDown


                _moneysReportCard.value = MoneysReportCard(
                    dispatchesReport,
                    outletReport
                )
            }
            _makingReport.value = false
        }
    }
}