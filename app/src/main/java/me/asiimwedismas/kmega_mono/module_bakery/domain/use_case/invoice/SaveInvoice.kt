package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.invoice

import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.common.di.CoroutineDispatchersProvider
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.InvoiceType
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.InvoiceType.*
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.*
import javax.inject.Inject

class SaveInvoice @Inject constructor(
    private val coroutineDispatchersProvider: CoroutineDispatchersProvider,
    private val agentRepository: AgentRepository,
    private val auditRepository: AuditRepository,
    private val dispatchesRepository: DispatchesRepository,
    private val expiredRepository: ExpiredRepository,
    private val outletRepository: OutletRepository,
    private val returnRepository: ReturnRepository
) {
    suspend operator fun invoke(invoice: BakeryInvoice){
        withContext(coroutineDispatchersProvider.io){
            when(InvoiceType.fromString(invoice.type!!)){
                DISPATCH -> dispatchesRepository.saveDispatch(invoice)
                OUTLET_DELIVERY -> outletRepository.saveDelivery(invoice)
                AGENT_DELIVERY -> agentRepository.saveAgentDelivery(invoice)
                RETURNS -> returnRepository.saveReturn(invoice)
                AUDIT -> auditRepository.saveAudit(invoice)
                EXPIRED -> expiredRepository.saveExpired(invoice)
            }
        }
    }
}