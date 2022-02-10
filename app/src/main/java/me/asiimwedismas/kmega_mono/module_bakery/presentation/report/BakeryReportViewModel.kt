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
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report.DatedSalesmenReport
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report.DatedFactoryReport
import java.util.*
import javax.inject.Inject

@HiltViewModel
class BakeryReportViewModel @Inject constructor(
    private val factoryReport: DatedFactoryReport,
    private val salesmenReport: DatedSalesmenReport,
    private val disptachers: CoroutineDispatchersProvider,
) : ViewModel() {
    val sd = SearchDates()

    var makeReportJob: Job? = null

    private val _dispatchesReportCard = mutableStateOf(DispatchesReportCard.createEmpty())
    val dispatchesReportCard: State<DispatchesReportCard> = _dispatchesReportCard

    private val _factoryReportCard = mutableStateOf(FactoryReportCard.emptyCard())
    val factoryReportCard: State<FactoryReportCard> = _factoryReportCard

    private val _showCalendar = mutableStateOf(false)
    val showCalendar: State<Boolean> = _showCalendar

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
            _factoryReportCard.value = FactoryReportCard.emptyCard()

            val factoryCardDef =
                async { factoryReport(sd.previousDate.value!!, sd.selectedDate.value!!) }
            val dispatchesCardDef = async { salesmenReport(sd.selectedDate.value!!) }

            val factReport = factoryCardDef.await()
            val dispatchesReport = dispatchesCardDef.await()

            with(dispatchesReport) {
                dispatches = factReport.dispatched
                returns = factReport.returned
                calulate()
            }

            _factoryReportCard.value = factReport
            _dispatchesReportCard.value = dispatchesReport
        }
    }
}