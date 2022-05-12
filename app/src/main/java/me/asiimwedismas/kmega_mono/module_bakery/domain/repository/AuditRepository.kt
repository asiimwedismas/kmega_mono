package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice

interface AuditRepository {

    suspend fun delete(invoiceID: String)

    suspend fun saveAudit(invoice: BakeryInvoice)

    suspend fun getAuditForOutletForDate(outletID: String, date: String): BakeryInvoice

    suspend fun getAuditForFactoryForDate(date: String): BakeryInvoice
}