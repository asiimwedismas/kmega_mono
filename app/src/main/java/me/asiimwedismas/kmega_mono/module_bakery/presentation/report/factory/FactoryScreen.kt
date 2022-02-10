package me.asiimwedismas.kmega_mono.module_bakery.presentation.report.factory

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryReportCard
import me.asiimwedismas.kmega_mono.module_bakery.presentation.report.BakeryReportViewModel
import me.asiimwedismas.kmega_mono.ui.common_components.CategoryRow
import me.asiimwedismas.kmega_mono.ui.common_components.StatementBody

@Composable
fun FactoryBody(
    reportCard: FactoryReportCard
) {
    val categories = listOf(
        ReportCategory("Shortage", reportCard.shortage, Color(0xFFFF6951)),
        ReportCategory("Dispatches", reportCard.dispatched, Color(0xFF004940)),
        ReportCategory("In-house damages", reportCard.expired, Color(0xFF37EFBA)),
        ReportCategory("Counted balance", reportCard.audited, Color(0xFFFFD7D0)),
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