package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionsSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.totalGrossProfit
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.totalUsedIngredients
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.UsedIngredientsRepository
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject

class GetDatedFinances @Inject constructor(
    private val financesRepository: FinancesRepository,
    private val productionRepository: ProductionRepository,
    private val ingredientsRepository: UsedIngredientsRepository,
    private val dispatchersProvider: CoroutineDispatchersProvider,
) {
    suspend operator fun invoke(date: String, previousDate: String): BakeryDatedFinances =
        withContext(dispatchersProvider.io) {
            val outletHandoversDef = async { financesRepository.getOutletsDaySheetForDate(date) }
            val salesmenHandoversDef =
                async { financesRepository.getSalesmenHandoversForDate(date) }
            val transactionsSheetDef =
                async { financesRepository.getSafeTransactionSheetForDate(date) }

            val previousDateProductionDef =
                async { productionRepository.getProductionSheetForDate(previousDate) }

            val previousDayIngredientsDef =
                async { ingredientsRepository.getSheetForDate(previousDate) }

            val outletHandover = outletHandoversDef.await()
            val salesmenHandovers = salesmenHandoversDef.await()
            val transactionsSheet = transactionsSheetDef.await()
            val previousProduction = previousDateProductionDef.await()
            val previousDayIngredients = previousDayIngredientsDef.await()

            var collections: Long = 0
            val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
            val collectionList: MutableList<String> = ArrayList()
            outletHandover.outletStandingList.forEach {
                collectionList.add("${it.outlet_name} : ${numberFormat.format(it.cash_handover)}")
                collections += it.cash_handover
            }

            salesmenHandovers.forEach {
                collectionList.add("${it.document_author_name} : ${numberFormat.format(it.handover_ed)}")
                collections += it.handover_ed
            }

            val previousProductionGrossProfit = previousProduction.totalGrossProfit.toLong()
            val previousDaysFlour = previousDayIngredients.items.sumOf {
                if (it.name == "Flour") it.total_cost else 0
            }.toLong()

            return@withContext withContext(dispatchersProvider.default) {
                BakeryDatedFinances(
                    previousProductionGrossProfit,
                    previousDaysFlour,
                    collections,
                    collectionList,
                    transactionsSheet
                )
            }
        }
}

data class BakeryDatedFinances(
    val previousDayGrossProfit: Long,
    val previousDayFlour: Long,
    val collections: Long,
    val collectionsList: List<String>,
    val safeTransactionsSheet: SafeTransactionsSheet,
)