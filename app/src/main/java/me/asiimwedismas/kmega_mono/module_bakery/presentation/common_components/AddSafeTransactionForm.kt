package me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.TransactionCategory
import me.asiimwedismas.kmega_mono.ui.common_components.AutoCompleteTextView
import java.text.NumberFormat

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun AddSafeTransactionForm(
    label: String,
    onQueryChanged: (String) -> Unit,
    onExplanationChanged: (String) -> Unit,
    onAmountChanged: (String) -> Unit,
    onOptionSelected: (TransactionCategory) -> Unit,
    onExpandPredictions: () -> Unit,
    onImeAction: () -> Unit,
    onClearClick: () -> Unit,
    predictionsList: List<TransactionCategory>,
    query: String,
    explanation: String,
    amount: String,
    submit: Boolean,
    onAddItem: () -> Unit,
) {
    ItemInputBackground(
        modifier = Modifier.padding(start = 16.dp, end = 16.dp, top = 8.dp, bottom = 16.dp),
        elevate = true) {
        Column() {
            AutoCompleteTextView(
                label = label,
                query = query,
                onQueryChanged = onQueryChanged,
                predictions = predictionsList,
                onOptionSelected = onOptionSelected,
                onImeAction = onImeAction,
                onClearClick = onClearClick,
                onTogglePredictions = onExpandPredictions
            )
            Spacer(modifier = Modifier.height(8.dp))
            val keyboardController = LocalSoftwareKeyboardController.current
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    Text(text = "Explanation")
                },
                value = explanation,
                onValueChange = onExplanationChanged,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Text
                ),
                /*keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })*/
            )
            Spacer(modifier = Modifier.height(8.dp))
            TextField(
                modifier = Modifier.fillMaxWidth(),
                label = {
                    val formatAttempt = amount.toIntOrNull()
                    val formattedAmount =
                        if (formatAttempt != null)
                            NumberFormat.getInstance().format(formatAttempt)
                        else
                            ""
                    Text(text = "Amount $formattedAmount")
                },
                value = amount,
                onValueChange = onAmountChanged,
                colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
                maxLines = 1,
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                    keyboardType = KeyboardType.Number
                ),
                keyboardActions = KeyboardActions(onDone = {
                    keyboardController?.hide()
                })
            )
            Spacer(modifier = Modifier.height(8.dp))
            FilledTonalButton(
                onClick = onAddItem,
                enabled = submit,
            ) {
                Text("Save")
            }

        }
    }
}

class AddSafeTransactionFormState(
    private val label: String,
) {

    private val optionsList = TransactionCategory.options()

    var query by mutableStateOf("")
    var predictions by mutableStateOf(emptyList<TransactionCategory>())
    var inputExplanation by mutableStateOf("")
    var inputAmount by mutableStateOf("")
    var submit by mutableStateOf(false)

    var amount: Int? = null
    var selectedOption: TransactionCategory? = null

    private fun shouldEnableSave() {
        submit = (amount != null && amount != 0 && selectedOption != null)
    }

    fun onQueryChanged(newQuery: String) {
        query = newQuery
        optionsList.let {
            predictions = if (query.isNotEmpty())
                optionsList.filter { it.category.contains(query, true) }
            else
                emptyList()
        }
    }

    fun onAmountChanged(inputAmount: String) {
        this.inputAmount = inputAmount

        amount = if (inputAmount.isNotBlank()) {
            inputAmount.toIntOrNull()
        } else {
            0
        }
        shouldEnableSave()
    }

    fun onExplanationChanged(inputExplanation: String) {
        this.inputExplanation = inputExplanation
        shouldEnableSave()
    }

    fun onCategorySelected(category: TransactionCategory) {
        query = category.toString()
        predictions = emptyList()
        selectedOption = category
        shouldEnableSave()
    }

    fun onTogglePredictions() {
        predictions = when {
            predictions.isEmpty() -> optionsList
            query.isNotBlank() -> optionsList.filter { it.category.contains(query) }
            else -> emptyList()
        }
    }

    fun clearAutoCompleteField() {
        query = ""
        selectedOption = null
        predictions = emptyList()
        shouldEnableSave()
    }

    private fun clearInputAmount() {
        inputAmount = ""
        amount = 0
        shouldEnableSave()
    }

    private fun clearExplanation() {
        inputExplanation = ""
    }

    fun clearInputs() {
        clearExplanation()
        clearAutoCompleteField()
        clearInputAmount()
    }


}