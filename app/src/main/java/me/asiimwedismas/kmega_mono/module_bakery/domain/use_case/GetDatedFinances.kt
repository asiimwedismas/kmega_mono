package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.SafeTransactionsSheet
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.totalGrossProfit
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductionRepository
import java.text.NumberFormat
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class GetDatedFinances @Inject constructor(
    private val financesRepository: FinancesRepository,
    private val productionRepository: ProductionRepository,
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

            val outletHandover = outletHandoversDef.await()
            val salesmenHandovers = salesmenHandoversDef.await()
            val transactionsSheet = transactionsSheetDef.await()
            val previousProduction = previousDateProductionDef.await()

            var collections: Long = 0
            val numberFormat = NumberFormat.getNumberInstance(Locale.UK)
            val collectionList : MutableList<String> = ArrayList()
            outletHandover.outletStandingList.forEach {
                collectionList.add("${it.outlet_name} : ${numberFormat.format(it.cash_handover)}")
                collections += it.cash_handover
            }

            salesmenHandovers.forEach {
                collectionList.add("${it.document_author_name} : ${numberFormat.format(it.handover_ed)}")
                collections += it.handover_ed
            }

            val previousProductionGrossProfit = previousProduction.totalGrossProfit.toLong()

            return@withContext withContext(dispatchersProvider.default) {
                BakeryDatedFinances(
                    previousProductionGrossProfit,
                    collections,
                    collectionList,
                    transactionsSheet
                )
            }
        }
}

data class BakeryDatedFinances(
    val previousDayGrossProfit: Long,
    val collections: Long,
    val collectionsList: List<String>,
    val safeTransactionsSheet: SafeTransactionsSheet,
)