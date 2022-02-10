package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.bakery_report

import kotlinx.coroutines.async
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryReportCard
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.*
import javax.inject.Inject

class DatedFactoryReport @Inject constructor(
    private val productionRepository: ProductionRepository,
    private val dispatchesRepository: DispatchesRepository,
    private val returnRepository: ReturnRepository,
    private val expiredRepository: ExpiredRepository,
    private val auditRepository: AuditRepository,
    private val dispatchers: CoroutineDispatchersProvider
) {
    suspend operator fun invoke(previous: String, date: String): FactoryReportCard =
        withContext(dispatchers.io) {
            val preProductionDef =
                async { productionRepository.getProductionSheetForDate(previous) }
            val preAuditDef = async { auditRepository.getAuditForFactoryForDate(previous) }
            val productionDef = async { productionRepository.getProductionSheetForDate(date) }
            val dispatchedDef = async { dispatchesRepository.getDispatchesForDate(date) }
            val returnedDef = async { returnRepository.getReturnsForDate(date) }
            val factoryExpiredDef = async { expiredRepository.getExpiredForFactoryForDate(date) }
            val factoryAuditDef = async { auditRepository.getAuditForFactoryForDate(date) }

            val preProduction = preProductionDef.await()
            val preAudit = preAuditDef.await()
            val production = productionDef.await()
            val dispatched = dispatchedDef.await()
            val returned = returnedDef.await()
            val factoryExpired = factoryExpiredDef.await()
            val factoryAudit = factoryAuditDef.await()

            return@withContext withContext(dispatchers.default) {
                FactoryReportCard(
                    preProducedList = listOf(preProduction),
                    producedList = listOf(production),
                    dispatchedList = dispatched,
                    returnedList = returned,
                    expiredList = factoryExpired,
                    preAuditedList = listOf(preAudit),
                    auditedList = listOf(factoryAudit)
                )
            }
        }
}
