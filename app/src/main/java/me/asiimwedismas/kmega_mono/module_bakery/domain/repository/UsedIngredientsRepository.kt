package me.asiimwedismas.kmega_mono.module_bakery.domain.repository

import me.asiimwedismas.kmega_mono.module_bakery.domain.model.UsedIngredientsSheet

interface UsedIngredientsRepository {
    suspend fun delete(sheetID: String)

    suspend fun save(usedIngredientsSheet: UsedIngredientsSheet)

    suspend fun getSheetForDate(date: String): UsedIngredientsSheet

    suspend fun getSheetsInRange(from: Long, to: Long): List<UsedIngredientsSheet>
}