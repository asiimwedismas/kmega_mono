package me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoiceItem
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.InvoiceType
import java.text.NumberFormat
import java.util.*


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun InvoiceItemHolder(
    item: BakeryInvoiceItem,
    invoiceType: InvoiceType
) {

    val numberFormat = NumberFormat.getNumberInstance(Locale.UK)

    ListItem(
        text = {
            Text(
                text = "${item.product_name} (${item.qty})",
                style = MaterialTheme.typography.bodyLarge
            )
        },
        trailing = {
            Text(
                text = when(invoiceType){
                    InvoiceType.DISPATACH -> numberFormat.format(item.total_factory_sale)
                    InvoiceType.OUTLET_DELIVERY -> numberFormat.format(item.total_outlet_sale)
                    InvoiceType.AGENT_DELIVERY -> numberFormat.format(item.total_agent_sale)
                    InvoiceType.PRODUCTION -> numberFormat.format(item.total_factory_sale)
                    InvoiceType.RETURNS -> numberFormat.format(item.total_factory_sale)
                    InvoiceType.AUDIT -> numberFormat.format(item.total_factory_sale)
                    InvoiceType.EXPIRED -> numberFormat.format(item.total_factory_sale)
                },
                style = MaterialTheme.typography.bodyLarge
            )
        }
    )
}