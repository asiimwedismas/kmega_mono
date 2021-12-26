package me.asiimwedismas.bakery_module.domain.model

import java.util.ArrayList

data class FieldExpenditureSheet(
    var document_id: String,
    var document_author_id: String,
    var document_author_name: String,
    var date: String,
    var utc: Long,
    var expenditures: List<SimpleExpenditure> = ArrayList()
)