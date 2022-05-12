package me.asiimwedismas.kmega_mono.common

import androidx.lifecycle.MutableLiveData
import java.text.DateFormat
import java.util.*

class SearchDates(
    private val dateFormat: DateFormat = DateFormat.getDateInstance(DateFormat.MEDIUM, Locale.US),
    calenderInstance: MutableLiveData<Calendar> = MutableLiveData(Calendar.getInstance()),
) {

    val selectedDate: MutableLiveData<String> = MutableLiveData<String>("")
    val previousDate: MutableLiveData<String> = MutableLiveData<String>("")
    val nextDate: MutableLiveData<String> = MutableLiveData<String>("")
    var instance: MutableLiveData<Calendar> = calenderInstance
        set(calenderInstance) {
            field = calenderInstance
            setDates()
        }

    init {
        setDates()
    }

    private fun setDates() {
        instance.value?.let {
            selectedDate.value = dateFormat.format(it.time)
            previousDate.value = dateFormat.format(it.getPreviousDate().time)
            nextDate.value = dateFormat.format(it.getNextDate().time)
        }
    }
}

fun Calendar.getPreviousDate(): Calendar {
    return offSetDate(-1, this)
}

fun Calendar.getNextDate(): Calendar {
    return offSetDate(1, this)
}

private fun offSetDate(offSet: Int, _calendar: Calendar): Calendar {
    val calendar = Calendar.getInstance()
    calendar.time = _calendar.time
    calendar.add(Calendar.DAY_OF_YEAR, offSet)
    return calendar
}