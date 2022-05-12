package me.asiimwedismas.kmega_mono.module_bakery.presentation.common_components

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.background
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.ListItem
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MaterialTheme.typography
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoiceItem
import java.text.NumberFormat
import java.util.*

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun <T> InvoiceProductHolder(
    onDeleted: (T, Int) -> Unit,
    deletable: Boolean = true,
    item: T,
    product: String,
    qty: Int,
    amount: Int,
    position: Int,
    numberFormat: NumberFormat = NumberFormat.getNumberInstance(Locale.UK),
) {
    ListItem(
        text = {
            Text(
                text = "$product ($qty)",
                style = typography.titleMedium
            )
        },
        secondaryText = {
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Text(
                    text = "Ugx ${numberFormat.format(amount)}",
                    style = typography.titleSmall,
                    fontWeight = FontWeight.Light
                )
            }
        },
        trailing = {
            IconButton(
                onClick = { onDeleted(item, position) }
            ) {
                Icon(imageVector = Icons.Outlined.Delete,
                    contentDescription = "delete item")
            }
        }
    )
}

@Preview(showBackground = true)
@Preview(showBackground = true, uiMode = UI_MODE_NIGHT_YES)
@Composable
fun PreviewItemHolder() {
    val dummyItem = BakeryInvoiceItem(
        product_name = "Bread",
        qty = 10000,
        total_factory_sale = 44000
    )
    InvoiceProductHolder(
        onDeleted = { _, _ -> },
        deletable = true,
        item = dummyItem,
        product = dummyItem.product_name,
        qty = dummyItem.qty,
        position = 1,
        amount = dummyItem.total_factory_sale
    )
}