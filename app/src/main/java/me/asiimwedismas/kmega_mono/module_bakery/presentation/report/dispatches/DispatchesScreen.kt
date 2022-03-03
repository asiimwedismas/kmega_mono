package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.dispatches

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.DispatchesReportCard
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody
import me.asiimwedismas.kmega_mono.ui.theme.*

@Composable
fun DispatchesBody(
    reportCard: DispatchesReportCard,
) {
    val categories = listOf(
        ReportCategory("Unaccounted for", reportCard.unaccounted, ColorsCyan200),
        ReportCategory("Salesmen shortages", reportCard.salesmenShortages, ColorsCyan200),
        ReportCategory("Cash handovers", reportCard.handovers, Pink40),
        ReportCategory("Outlet deliveries", reportCard.outletsDeliveries, ColorsOrange200),
        ReportCategory("Agent discounts", reportCard.agentDiscounts, ColorsYellow200),
        ReportCategory("Debit sales", reportCard.debitSales, ColorsGreen200),
        ReportCategory("Field expenditures", reportCard.fieldExpenditures, ColorsBlue200),
        ReportCategory("Returns", reportCard.returns, ColorsIndigo200),
        ReportCategory("Field replacements", reportCard.fieldReplacements, ColorsViolet200),
    )

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Dispatches statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = reportCard.dispatches,
        circleLabel = "Dispatches",
        rows = { category ->
            CategoryRow(name = category.name, amount = category.amount, color = category.color)
        }
    )
}