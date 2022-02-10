package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice

interface ExpiredRepository {

    suspend fun saveExpired(invoice: BakeryInvoice)

    suspend fun getExpiredForSalesmanForDate(salesmanID: String, date: String): List<BakeryInvoice>

    suspend fun getExpiredForOutletForDate(outletID: String, date: String): List<BakeryInvoice>

    suspend fun getExpiredForFactoryForDate(date: String): List<BakeryInvoice>
    suspend fun getExpiredForFieldForDate(date: String): List<BakeryInvoice>

    suspend fun getAllExpiredInRange(from: Long, to: Long): List<BakeryInvoice>

}