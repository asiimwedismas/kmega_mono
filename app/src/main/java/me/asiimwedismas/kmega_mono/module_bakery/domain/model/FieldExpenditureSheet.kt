package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import java.util.ArrayList

data class FieldExpenditureSheet(
    var document_id: String = "",
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var date: String = "",
    var utc: Long = 0,
    var salesman_id: String = "",
    var salesman_name: String = "",
    var expenditures: MutableList<SimpleExpenditure> = ArrayList(),
)