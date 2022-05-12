package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.moneys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.MoneysReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.ProfitsReportCard
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody

@Composable
fun MoneysScreen(
    profitsReportCard: ProfitsReportCard
) {
    val categories = profitsReportCard.reportCategories

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Moneys statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = profitsReportCard.grossProfit,
        circleLabel = "Gross profits",
        rows = { category ->
            CategoryRow( name = category.name, amount = category.amount, color = category.color)
        }
    )

}