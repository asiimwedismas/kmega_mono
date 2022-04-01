package me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Text
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
import me.asiimwedismas.kmega_mono.ui.common_components.AutoCompleteTextView

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T> AddProductForm(
    label: String,
    qtyLabel: String = "Qty",
    query: String,
    onQueryChanged: (String) -> Unit,
    onOptionSelected: (T) -> Unit,
    onImeAction: () -> Unit,
    onClearClick: () -> Unit,
    predictionsList: List<T>,
    productQty: String,
    onQtyChanged: (String) -> Unit,
    submit: Boolean,
    onAddItem: () -> Unit,
) {
    ItemInputBackground(elevate = true) {
        Column {
            AutoCompleteTextView(
                label = label,
                query = query,
                onQueryChanged = onQueryChanged,
                predictions = predictionsList,
                onOptionSelected = onOptionSelected,
                onImeAction = onImeAction,
                onClearClick = onClearClick
            )
            Spacer(modifier = Modifier.height(8.dp))
            val keyboardController = LocalSoftwareKeyboardController.current
            OutlinedTextField(
                label = {
                    Text(text = qtyLabel)
                },
                value = productQty,
                onValueChange = onQtyChanged,
                colors = TextFieldDefaults.textFieldColors(backgroundColor = Color.Transparent),
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
                shape = CircleShape,
                enabled = submit,
            ) {
                Text("Save")
            }
        }
    }
}

class AddProductFormState<T>(
    private val label: String,
    var optionsList: List<T>,
) {
    var query by mutableStateOf("")
    var predictions by mutableStateOf(emptyList<T>())
    var qtyInputHint by mutableStateOf("")
    var qtyInput by mutableStateOf("")
    var submit by mutableStateOf(false)

    var qty: Float = 0F
    var selectedOption: T? = null

    private fun shouldEnableSave() {
        submit = (qty != 0F && selectedOption != null)
    }

    fun onQueryChanged(newQuery: String, predicate: (T, String) -> Boolean) {
        query = newQuery
        optionsList.let {
            predictions = if (query.isNotEmpty())
                optionsList.filter { predicate(it, query) }
            else
                emptyList()
        }
    }

    fun onChangeQty(inputQty: String) {
        this.qtyInput = inputQty

        qty = if (inputQty.isNotBlank()) {
            inputQty.toFloat()
        } else {
            0F
        }
        shouldEnableSave()
    }

    fun onProductSelected(product: T) {
        query = product.toString()
        predictions = emptyList()
        selectedOption = product
        shouldEnableSave()
    }

    fun clearAutoCompleteField() {
        query = ""
        selectedOption = null
        clearPredictions()
        shouldEnableSave()
    }

    fun clearPredictions() {
        predictions = emptyList()
    }

    private fun clearProductQtyInput() {
        qtyInput = ""
        qty = 0F
        shouldEnableSave()
    }

    fun clearInputs() {
        clearAutoCompleteField()
        clearProductQtyInput()
    }
}

