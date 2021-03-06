package me.asiimwedismas.kmega_mono.ui.common_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.window.PopupProperties

@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
@Composable
fun <T> AutoCompleteTextView(
    modifier: Modifier = Modifier,
    query: String,
    label: String,
    onClearClick: () -> Unit = {},
    onTogglePredictions: () -> Unit = {},
    onQueryChanged: (String) -> Unit,
    predictions: List<T> = emptyList(),
    onOptionSelected: (T) -> Unit = {},
    onImeAction: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    ExposedDropdownMenuBox(
        expanded = predictions.isNotEmpty(),
        onExpandedChange = {
            onTogglePredictions()
        }
    ) {
        TextField(
            modifier = modifier
                .fillMaxWidth(),
            value = query,
            onValueChange = onQueryChanged,
            label = { Text(label) },
            singleLine = true,
            trailingIcon = {
                if (query.isNotEmpty()) {
                    IconButton(onClick = { onClearClick() }) {
                        Icon(imageVector = Icons.Filled.Clear, contentDescription = "clear")
                    }
                } else {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = predictions.isNotEmpty()
                    )
                }
            },
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text),
            keyboardActions = KeyboardActions(onDone = {
                onImeAction()
                keyboardController?.hide()
            }),
            colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
        )
        if (predictions.isNotEmpty()) {
            DropdownMenu(
                modifier = Modifier.exposedDropdownSize(),
                expanded = predictions.isNotEmpty(),
                onDismissRequest = {
                    onTogglePredictions()
                },
                properties = PopupProperties(focusable = false)
            ) {
                predictions.forEach { prediction ->
                    DropdownMenuItem(
                        text = {
                            Text(text = prediction.toString())
                        },
                        onClick = {
                            onOptionSelected(prediction)
                        }
                    )
                }
            }
        }
    }
}


@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun InputText(
    text: String,
    onTextChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    onImeAction: () -> Unit = {},
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    TextField(
        value = text,
        onValueChange = onTextChange,
        colors = TextFieldDefaults.textFieldColors(containerColor = Color.Transparent),
        maxLines = 1,
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onImeAction()
            keyboardController?.hide()
        }),
        modifier = modifier
    )
}