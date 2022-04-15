package me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.AlignmentLine
import androidx.compose.ui.layout.VerticalAlignmentLine
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.module_bakery.data.sampleData.sampleProducts
import me.asiimwedismas.kmega_mono.ui.common_components.AutoCompleteTextView
import me.asiimwedismas.kmega_mono.ui.theme.Kmega_monoTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun <T> AddProductForm(
    modifier: Modifier = Modifier,
    label: String,
    qtyLabel: String = "Quantity",
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
    ItemInputBackground(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 16.dp),
        elevate = true) {
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
            val keyboardController = LocalSoftwareKeyboardController.current
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.Center
            ) {
                TextField(
                    modifier = Modifier.weight(1f),
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
                Spacer(modifier = Modifier.width(8.dp))
                FilledTonalButton(
                    onClick = onAddItem,
                    enabled = submit,
                ) {
                    Text("Save")
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "NightPreview", uiMode = UI_MODE_NIGHT_YES)
@Preview(showBackground = true, name = "LightPreview")
@Composable
fun PreviewAddProductForm() {
    val formState by remember {
        mutableStateOf(AddProductFormState("Add product", sampleProducts))
    }

    Kmega_monoTheme {
        AddProductForm(
            label = "Add product",
            query = formState.query,
            onQueryChanged = {
                formState.onQueryChanged(it) { item, query ->
                    item.product.contains(query, true)
                }
            },
            onOptionSelected = {
                formState.onProductSelected(it)
            },
            onImeAction = { },
            onClearClick = {
                formState.clearAutoCompleteField()
            },
            predictionsList = formState.predictions,
            productQty = formState.qtyInput,
            onQtyChanged = {
                formState.onChangeQty(it)
            },
            submit = formState.submit,
            onAddItem = {

            }
        )
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

    var qty: Float? = null
    var selectedOption: T? = null

    private fun shouldEnableSave() {
        submit = (qty != null && qty != 0F && selectedOption != null)
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

    fun onTogglePredictions() {
        predictions = when {
            predictions.isEmpty() -> optionsList
            else -> emptyList()
        }
    }

    fun onChangeQty(inputQty: String) {
        this.qtyInput = inputQty

        qty = if (inputQty.isNotBlank()) {
            inputQty.toFloatOrNull()
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

