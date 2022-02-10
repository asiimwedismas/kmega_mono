package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.DispatchesReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.*
import javax.inject.Inject

class DatedSalesmenReport @Inject constructor(
    private val outletRepository: OutletRepository,
    private val agentRepository: AgentRepository,
    private val expiredRepository: ExpiredRepository,
    private val dispatchers: CoroutineDispatchersProvider,
) {
    suspend operator fun invoke(date: String): DispatchesReportCard = withContext(dispatchers.io) {
        val outletDef = async { outletRepository.getDeliveriesForDate(date) }
        val agentDef = async { agentRepository.getAgentDeliveriesDated(date) }
        val expiredDef = async { expiredRepository.getExpiredForFieldForDate(date) }

        val outlet = outletDef.await()
        val agents = agentDef.await()
        val expired = expiredDef.await()

        return@withContext withContext(dispatchers.default) {
            DispatchesReportCard(
                outletList = outlet,
                agentList = agents,
                expiredList = expired
            )
        }
    }
}