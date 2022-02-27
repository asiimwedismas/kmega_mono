package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.invoice

import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.InvoiceType
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.InvoiceType.*
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.*
import javax.inject.Inject

class DeleteInvoice  @Inject constructor(
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val agentRepository: AgentRepository,
    private val auditRepository: AuditRepository,
    private val dispatchesRepository: DispatchesRepository,
    private val expiredRepository: ExpiredRepository,
    private val outletRepository: OutletRepository,
    private val returnRepository: ReturnRepository
) {
    suspend operator fun invoke(invoiceID: String, type: String){
        withContext(coroutineDispatchersProvider.io){
            when(InvoiceType.fromString(type)){
                DISPATCH -> dispatchesRepository.delete(invoiceID)
                OUTLET_DELIVERY -> outletRepository.delete(invoiceID)
                AGENT_DELIVERY -> agentRepository.delete(invoiceID)
                RETURNS -> returnRepository.delete(invoiceID)
                AUDIT -> auditRepository.delete(invoiceID)
                EXPIRED -> expiredRepository.delete(invoiceID)
            }
        }
    }
}