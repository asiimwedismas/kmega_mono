package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class FieldExpenditure(
    var document_id: String? = null,
    var document_author_id: String? = null,
    var document_author_name: String? = null,
    var date: String = "",
    var utc: Long = 0,
    var salesman_id: String? = null,
    var salesman_name: String? = null,
    var expenditures: MutableList<SimpleExpenditure> = ArrayList(),
)

data class SimpleExpenditure(
    var explanation: String = "",
    var amount: Int = 0
)