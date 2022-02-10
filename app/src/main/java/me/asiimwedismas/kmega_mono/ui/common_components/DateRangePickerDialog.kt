package me.asiimwedismas.kmega_mono.ui.common_components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.datepicker.MaterialDatePicker
import androidx.core.util.Pair
import com.google.android.material.datepicker.CalendarConstraints


@Composable
fun DateRangePickerDialog(
    currentSelectedRange: Pair<Long, Long>,
    onRangeSelected: (Pair<Long, Long>) -> Unit,
    onDismiss: () -> Unit,
) {


    val activity = LocalContext.current as AppCompatActivity

    val calendarConstraints = CalendarConstraints
        .Builder()
        .setOpenAt(currentSelectedRange.first)
        .build()

    MaterialDatePicker
        .Builder
        .dateRangePicker()
        .setSelection(currentSelectedRange)
        .setCalendarConstraints(calendarConstraints)
        .build()
        .apply {
            this.addOnDismissListener {
                onDismiss()
            }
            this.addOnPositiveButtonClickListener { selection ->
                onRangeSelected(selection)
            }
        }
        .show(activity.supportFragmentManager, "Date range picker")

}