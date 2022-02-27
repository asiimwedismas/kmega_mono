package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice

interface AgentRepository {

    suspend fun delete(invoiceID: String)

    suspend fun saveAgentDelivery(invoice: BakeryInvoice)

    suspend fun getAgentDeliveriesBySalesmanForDate(salesmanID: String, date: String): List<BakeryInvoice>

    suspend fun getAgentDeliveriesInRange(from: Long, to: Long): List<BakeryInvoice>

    suspend fun getAgentDeliveriesDated(date: String): List<BakeryInvoice>
}