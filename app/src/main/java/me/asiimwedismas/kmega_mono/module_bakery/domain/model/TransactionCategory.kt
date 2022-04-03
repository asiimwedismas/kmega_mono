package me.asiimwedismas.kmega_mono.module_bakery.domain.model

data class TransactionCategory(
    var category: String,
    var isIngredient: Boolean = false,
) {
    override fun toString(): String {
        return category
    }

    companion object {
        fun options(): List<TransactionCategory> {
            val list: MutableList<TransactionCategory> = ArrayList()
            list.add(TransactionCategory("Asset Purchase"))
            list.add(TransactionCategory("Allowances"))
            list.add(TransactionCategory("Banked"))
            list.add(TransactionCategory("Debit repayment"))
            list.add(TransactionCategory("Drawing"))
            list.add(TransactionCategory("Electricity"))
            list.add(TransactionCategory("Flour", true))
            list.add(TransactionCategory("Fuel Vehicles & Oven"))
            list.add(TransactionCategory("Generator"))
            list.add(TransactionCategory("Ingredients", true))
            list.add(TransactionCategory("Lent out"))
            list.add(TransactionCategory("Machine maintenance & repair"))
            list.add(TransactionCategory("Miscellaneous"))
            list.add(TransactionCategory("National Water"))
            list.add(TransactionCategory("Office Stationary"))
            list.add(TransactionCategory("Packaging", true))
            list.add(TransactionCategory("Rent"))
            list.add(TransactionCategory("Safe Shortage"))
            list.add(TransactionCategory("Salaries"))
            list.add(TransactionCategory("Security"))
            list.add(TransactionCategory("Site maintenance"))
            list.add(TransactionCategory("Staff food"))
            list.add(TransactionCategory("Taxes AND Licenses"))
            list.add(TransactionCategory("Transport"))
            return list
        }
    }
}