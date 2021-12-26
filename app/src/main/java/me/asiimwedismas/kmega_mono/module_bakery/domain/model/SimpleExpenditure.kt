package me.asiimwedismas.bakery_module.domain.model

data class SimpleExpenditure(
    var salesman_id: String,
    var salesman_name: String,
    var explanation: String,
    var amount: Int
)