package me.asiimwedismas.kmega_mono.ui.common_components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.ContentAlpha
import androidx.compose.material.LocalContentAlpha
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import me.asiimwedismas.kmega_mono.ui.theme.TypographyM2
import java.text.NumberFormat
import java.util.*
import kotlin.math.abs

/**
 * A row representing the basic information of an Account.
 */
@Composable
fun CategoryRow(
    modifier: Modifier = Modifier,
    name: String,
    amount: Long,
    color: Color,
) {
    ReportBaseRow(
        modifier = modifier,
        color = color,
        title = name,
        amount = amount,
        negative = amount < 0
    )
}

@Composable
private fun ReportBaseRow(
    modifier: Modifier = Modifier,
    color: Color,
    title: String,
    amount: Long,
    negative: Boolean,
) {
    val currencySign = if (negative) "–Ugx " else "Ugx "
    val formattedAmount = formatAmount(abs(amount))
    Row(
        modifier = modifier
            .height(68.dp)
            .clearAndSetSemantics {
                contentDescription = ""
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        val typography = TypographyM2
        AccountIndicator(
            color = color,
            modifier = Modifier
        )
        Spacer(Modifier.width(12.dp))
        Column(Modifier) {
            Text(text = title, style = typography.body2)
            CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text(
                        text = currencySign,
                        style = typography.h6,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                    Text(
                        text = formattedAmount,
                        style = typography.h6,
                        modifier = Modifier.align(Alignment.CenterVertically)
                    )
                }
            }
        }
        Spacer(Modifier.weight(1f))
        Spacer(Modifier.width(16.dp))
        CompositionLocalProvider(LocalContentAlpha provides ContentAlpha.medium) {
            Icon(
                imageVector = Icons.Filled.ChevronRight,
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(24.dp)
            )
        }
    }
    LineDivider()
}

@Preview
@Composable
fun PreviewReportBaseRow() {
    ReportBaseRow(
        color = Color.Blue,
        title = "Title",
        amount = 1_000_000,
        negative = true
    )
}

/**
 * A vertical colored line that is used in a [ReportBaseRow] to differentiate categories.
 */
@Composable
private fun AccountIndicator(color: Color, modifier: Modifier = Modifier) {
    Spacer(
        modifier
            .size(4.dp, 36.dp)
            .background(color = color)
    )
}

@Composable
fun LineDivider(modifier: Modifier = Modifier) {
    Divider(thickness = 1.dp, modifier = modifier)
}

fun formatAmount(amount: Long): String {
    return NumberFormat.getInstance(Locale.UK).format(amount)
}

/**
 * Used with lists to create the animated circle.
 */
fun <E> List<E>.extractProportions(selector: (E) -> Long): List<Float> {
    val total = this.sumOf { selector(it).toDouble() }
    return this.map { (selector(it) / total).toFloat() }
}
