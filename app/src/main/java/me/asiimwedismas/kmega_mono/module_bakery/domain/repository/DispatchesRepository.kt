package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice

interface DispatchesRepository {
    suspend fun delete(invoiceID: String)

    suspend fun saveDispatch(invoice: BakeryInvoice)

    suspend fun getDispatchesForDate(date: String): List<BakeryInvoice>

    suspend fun getDispatchesForRange(from: Long, to: Long): List<BakeryInvoice>

    suspend fun getDispatchesForSalesmanForDate(salesmanID: String, date: String): List<BakeryInvoice>
    suspend fun getDispatchesForSalesmanForRange(salesmanID: String, from: Long, to: Long): List<BakeryInvoice>

}