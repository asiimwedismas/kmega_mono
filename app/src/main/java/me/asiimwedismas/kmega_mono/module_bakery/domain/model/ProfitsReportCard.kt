package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import me.asiimwedismas.kmega_mono.common.domain.ReportCategory
import me.asiimwedismas.kmega_mono.ui.theme.*
import java.util.*
import kotlin.math.abs

data class ProfitsReportCard(
    val factoryReportCard: FactoryReportCard,
    val dispatchesReportCard: DispatchesReportCard,
    val outletReportCard: OutletReportCard,
    val safeExpenditureSheet: SafeTransactionsSheet,
) {
    var grossProfit: Long = 0
        private set
    var profitBalance: Long = 0
        private set
    var reportCategories: MutableList<ReportCategory> = mutableListOf()
        private set

    init {
        val totalNonIngredientExpenditure = safeExpenditureSheet.totalOtherTransactions

        grossProfit =
            factoryReportCard.preProducted - factoryReportCard.preIngredients.sumOf { it.totalUsedIngredients }

        val expired =
            factoryReportCard.expired + dispatchesReportCard.fieldReplacements + outletReportCard.expired

        val shortages =
            abs(dispatchesReportCard.salesmenShortages) + abs(outletReportCard.auditedShortage)

        profitBalance = grossProfit - (
                totalNonIngredientExpenditure +
                        expired +
                        shortages +
                        dispatchesReportCard.fieldExpenditures +
                        dispatchesReportCard.agentDiscounts +
                        outletReportCard.expenditure
                )

        val transactionsMap: MutableMap<String, SafeTransactionItem> = TreeMap()
        safeExpenditureSheet.items.forEachIndexed { index, entry ->
            val category = entry.category
            if (transactionsMap.containsKey(category)) {
                transactionsMap[category]?.apply {
                    amount += entry.amount
                }
            } else {
                if (!entry.isIngredient) {
                    val newItem = SafeTransactionItem(
                        category = entry.category,
                        amount = entry.amount
                    )
                    transactionsMap.putIfAbsent(category, newItem)
                }
            }
        }

        reportCategories.add(
            ReportCategory(
                "Profit balance",
                profitBalance,
                ColorsGreen500
            )
        )

        reportCategories.add(
            ReportCategory(
                "Expired & Damages",
                expired,
                ColorsRed200
            )
        )

        reportCategories.add(
            ReportCategory(
                "Shortages",
                shortages,
                ColorsYellow200
            )
        )

        reportCategories.add(
            ReportCategory(
                "Outlet Expenditure",
                outletReportCard.expenditure,
                Purple40
            )
        )

        reportCategories.add(
            ReportCategory(
                "Field expenditures",
                dispatchesReportCard.fieldExpenditures,
                Purple40
            )
        )

        reportCategories.add(
            ReportCategory(
                "Agent discounts",
                dispatchesReportCard.agentDiscounts,
                ColorsViolet200
            )
        )

        val colors = listOf(
            ColorsRed200,
            ColorsOrange200,
            ColorsYellow200,
            ColorsBlue200,
            ColorsIndigo200,
            ColorsViolet200,
            ColorsCyan200,
            Purple40,
            PurpleGrey40,
            Pink40
        )



        transactionsMap.entries.forEachIndexed { i, entry ->
            reportCategories.add(
                ReportCategory(
                    entry.value.category,
                    entry.value.amount.toLong(),
                    ColorsCyan200
                )
            )
        }

    }
}
