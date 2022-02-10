package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.dispatches

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.DispatchesReportCard
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody

@Composable
fun DispatchesBody(
    reportCard: DispatchesReportCard
) {
    val categories = listOf(
        ReportCategory("Cash handovers", 0, Color(0xFFFFD7D0)),
        ReportCategory("Outlet deliveries", reportCard.outlets, Color(0xFFFFD7D0)),
        ReportCategory("Agent discounts", reportCard.agentDiscounts, Color(0xFF37EFBA)),
        ReportCategory("Debit sales", 0, Color(0xFF37EFBA)),
        ReportCategory("Field expenditures", 0, Color(0xFF37EFBA)),
        ReportCategory("Returns", reportCard.returns, Color(0xFF37EFBA)),
        ReportCategory("Field replacements", reportCard.expired, Color(0xFFFFD7D0)),
        ReportCategory("Shortage", reportCard.shortage, Color(0xFFFF6951)),
    )

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Dispatches statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = reportCard.dispatches,
        circleLabel = "Dispatches",
        rows = { category ->
            CategoryRow( name = category.name, amount = category.amount, color = category.color)
        }
    )
}