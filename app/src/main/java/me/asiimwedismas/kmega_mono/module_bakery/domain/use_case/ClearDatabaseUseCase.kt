package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
import javax.inject.Inject

class ClearDatabaseUseCase @Inject constructor(
    private val database: BakeryDatabase
) {
    operator fun invoke(){
        database.clearAllTables()
    }
}