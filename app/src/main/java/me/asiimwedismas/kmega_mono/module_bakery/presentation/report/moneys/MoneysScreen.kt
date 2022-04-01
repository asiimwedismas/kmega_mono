package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.moneys

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.MoneysReportCard
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody
import me.asiimwedismas.kmega_mono.ui.theme.*

@Composable
fun MoneysScreen(
    moneysReportCard: MoneysReportCard
) {
    val categories = emptyList<ReportCategory>()

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Moneys statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = moneysReportCard.totalCollections,
        circleLabel = "Total collections",
        rows = { category ->
            CategoryRow( name = category.name, amount = category.amount, color = category.color)
        }
    )

}