package me.asiimwedismas.bakery_module.domain.model

import java.util.ArrayList

data class FactoryProductionSheet(
    var date: String,
    var document_id: String,
    var document_author_id: String,
    var document_author_name: String,
    var utc: Long = 0,
    var lock_status: Boolean = false,
    var items: List<FactoryProductionItem> = ArrayList()
)