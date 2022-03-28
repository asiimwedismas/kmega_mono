package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.dispatches

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ExpandLess
import androidx.compose.material.icons.outlined.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveableStateHolder
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.DispatchedBreakDown
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.DispatchedItem
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DispatchedBreakDownScreen(
    dispatchedBreakDown: DispatchedBreakDown,
) {
    val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
    val itemsList: List<DispatchedItem> = dispatchedBreakDown.dispatchedItemsMap.values.toList()
    LazyColumn {
        items(
            items = itemsList,
            key = { it.product!! }
        ) { item ->
            var isExpanded by remember {
                mutableStateOf(false)
            }
            Column(Modifier.fillMaxWidth()) {
                ListItem(
                    text = {
                        Text(
                            text = "${item.product!!} (${item.unaccountedBalance})",
                            style = MaterialTheme.typography.titleMedium
                        )
                    },
                    secondaryText = {
                        if(isExpanded){
                            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                                Column(Modifier.fillMaxWidth()) {
                                    Text(
                                        text = "Previous counted balance = ${item.preAudited}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Previous returns = ${item.preReturned}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Previous production = ${item.preProduced}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Total opening stock = ${item.openingBalance}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Dispatches = ${item.dispatchedTotal}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Factory damages = ${item.countedFactoryDamages}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Counted closing balance = ${item.countedBalance}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Text(
                                        text = "Expected balance = ${item.expectedBalance}",
                                        style = MaterialTheme.typography.titleSmall,
                                        fontWeight = FontWeight.Light
                                    )
                                    Spacer(modifier = Modifier.height(8.dp))
                                    for (simpleDispatch in item.dispatchList) {
                                        Column(Modifier.fillMaxWidth()) {
                                            Text(
                                                text = "-- ${simpleDispatch.salesman} : ${simpleDispatch.receivedQty}",
                                                style = MaterialTheme.typography.titleSmall,
                                                fontWeight = FontWeight.Light
                                            )
                                        }
                                    }
                                }
                            }
                        }
                    },
                     trailing = {
                         IconButton(
                             onClick = { isExpanded = !isExpanded }
                         ) {
                             Icon(
                                 imageVector = if (isExpanded) Icons.Outlined.ExpandLess else Icons.Outlined.ExpandMore,
                                 contentDescription = "")
                         }
                     }
                )

            }
        }
    }
}