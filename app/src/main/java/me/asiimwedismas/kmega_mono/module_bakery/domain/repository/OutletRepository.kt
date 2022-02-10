package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice

interface OutletRepository {

    suspend fun saveDelivery(invoice: BakeryInvoice)

    suspend fun getDeliveriesForDate(date: String): List<BakeryInvoice>

    suspend fun getDeliveriesInRange(from: Long, to: Long): List<BakeryInvoice>

    suspend fun getDeliveriesForOutletDated(outletID: String, date: String): List<BakeryInvoice>

    suspend fun getDeliveriesOfSalesmanDated(
        salesmanID: String,
        date: String,
    ): List<BakeryInvoice>
}