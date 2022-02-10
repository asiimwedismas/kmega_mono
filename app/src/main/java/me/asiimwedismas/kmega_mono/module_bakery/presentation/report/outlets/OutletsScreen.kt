package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.outlets

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
fun OutletsBody(
) {
    val categories = listOf(
        ReportCategory("Cash handovers", 0, Color(0xFFFFD7D0)),
        ReportCategory("Expenditure", 0, Color(0xFFFFD7D0)),
        ReportCategory("Expired", 0, Color(0xFF37EFBA)),
        ReportCategory("Verified stock balance", 0, Color(0xFF37EFBA)),
        ReportCategory("Shortage", 0, Color(0xFFFF6951)),
    )

    StatementBody(
        modifier = Modifier.clearAndSetSemantics { contentDescription = "Dispatches statement" },
        items = categories,
        amounts = { category -> category.amount },
        colors = { category -> category.color },
        amountsTotal = 0,
        circleLabel = "BF + Deliveries",
        rows = { category ->
            CategoryRow( name = category.name, amount = category.amount, color = category.color)
        }
    )
}