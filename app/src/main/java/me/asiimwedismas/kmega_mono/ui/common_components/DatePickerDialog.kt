package me.asiimwedismas.kmega_mono.ui.common_components

import androidx.appcompat.app.AppCompatActivity
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.MaterialDatePicker
import java.util.Calendar

@Composable
fun DatePickerDialog(
    currentSelected: Long = Calendar.getInstance().timeInMillis,
    onDateSelected: (Calendar) -> Unit,
    onDismiss: () -> Unit,
) {
    val calendarConstraints = CalendarConstraints.Builder().setOpenAt(currentSelected).build()
    val activity = LocalContext.current as AppCompatActivity

    MaterialDatePicker
        .Builder
        .datePicker()
        .setSelection(currentSelected)
        .setCalendarConstraints(calendarConstraints)
        .build()
        .apply {
            this.addOnPositiveButtonClickListener {
                val instance = Calendar.getInstance()
                instance.timeInMillis = selection!!
                onDateSelected(instance)
            }
            this.addOnDismissListener {
                onDismiss()
            }
        }
        .show(activity.supportFragmentManager, "Date Picker")
}