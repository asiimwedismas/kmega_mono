package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice

interface ReturnRepository {

    suspend fun saveReturn(invoice: BakeryInvoice)

    suspend fun getReturnForSalesmanForDate(salesmanID: String, date: String): BakeryInvoice

    suspend fun getReturnsForDate(date: String): List<BakeryInvoice>

    suspend fun getReturnsInRange(from: Long, to: Long): List<BakeryInvoice>
}