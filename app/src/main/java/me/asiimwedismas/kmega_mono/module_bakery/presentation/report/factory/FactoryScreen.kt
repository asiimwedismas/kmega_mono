package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.factory

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryReportCard
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody
import me.asiimwedismas.kmega_mono.ui.theme.*

@Composable
fun FactoryBody(
    reportCard: FactoryReportCard
) {
    val categories = listOf(
        ReportCategory("Unaccounted for", reportCard.unaccountedFor, ColorsRed200),
        ReportCategory("Dispatches", reportCard.dispatched, ColorsBlue200),
        ReportCategory("In-house damages", reportCard.expired, ColorsYellow200),
        ReportCategory("Counted balance", reportCard.audited, ColorsGreen200),
    )

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Factory statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = reportCard.openingStock,
        circleLabel = "Opening Stock",
        rows = { category ->
            CategoryRow( name = category.name, amount = category.amount, color = category.color)
        }
    )
}