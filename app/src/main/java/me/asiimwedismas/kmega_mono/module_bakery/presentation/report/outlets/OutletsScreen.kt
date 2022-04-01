package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.outlets

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletReportCard
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody
import me.asiimwedismas.kmega_mono.ui.theme.*

@Composable
fun OutletsBody(
    reportCard: OutletReportCard
) {
    val categories = listOf(
        ReportCategory("Unaccounted for", reportCard.unaccountedFor, ColorsIndigo200),
        ReportCategory("Cash handovers", reportCard.handovers, ColorsBlue500),
        ReportCategory("Expenditure", reportCard.expenditure, ColorsGreen500),
        ReportCategory("Expired", reportCard.expired, ColorsYellow200),
        ReportCategory("Verified stock balance", reportCard.closingStock, ColorsViolet500),
        ReportCategory("Audited shortage", reportCard.auditedShortage, ColorsRed500),
    )

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Dispatches statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = reportCard.openingStock,
        circleLabel = "BF + Deliveries",
        rows = { category ->
            CategoryRow( name = category.name, amount = category.amount, color = category.color)
        }
    )
}