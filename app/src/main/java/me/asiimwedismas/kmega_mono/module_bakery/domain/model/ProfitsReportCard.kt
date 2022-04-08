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
                Green500
            )
        )

        reportCategories.add(
            ReportCategory(
                "Expired & Damages",
                expired,
                Red200
            )
        )

        reportCategories.add(
            ReportCategory(
                "Shortages",
                shortages,
                Yellow200
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
                Violet200
            )
        )

        val colors = listOf(
            Red200,
            Orange200,
            Yellow200,
            Blue200,
            Indigo200,
            Violet200,
            Cyan200,
            Purple40,
            PurpleGrey40,
            Pink40
        )



        transactionsMap.entries.forEachIndexed { i, entry ->
            reportCategories.add(
                ReportCategory(
                    entry.value.category,
                    entry.value.amount.toLong(),
                    Cyan200
                )
            )
        }

    }
}
