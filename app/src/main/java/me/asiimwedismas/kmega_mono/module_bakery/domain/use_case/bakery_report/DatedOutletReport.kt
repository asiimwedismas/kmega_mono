package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.OutletReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.FinancesRepository
import javax.inject.Inject

class DatedOutletReport @Inject constructor(
    private val dispatchersProvider: CoroutineDispatchersProvider,
    private val financesRepository: FinancesRepository,
) {
    suspend operator fun invoke(date: String): OutletReportCard =
        withContext(dispatchersProvider.io) {
            val outletStandingsPred = async { financesRepository.getOutletsDaySheetForDate(date) }
            val outletSheet = outletStandingsPred.await()

            withContext(dispatchersProvider.default) {
                return@withContext OutletReportCard(
                    outletsDaySheet = outletSheet
                )
            }
        }
}