package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryInvoice
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.FactoryProductionSheet

interface ProductionRepository {
    suspend fun delete(sheetID: String)

    suspend fun saveProductionSheet(productionSheet: FactoryProductionSheet)

    suspend fun getProductionSheetForDate(date: String): FactoryProductionSheet

    suspend fun getProductionSheetsInRange(from: Long, to: Long): List<FactoryProductionSheet>
}