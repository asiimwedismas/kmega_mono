package me.asiimwedismas.kmega_mono.module_bakery.domain.model

import java.util.*
import kotlin.collections.ArrayList

data class SimpleDispatch(
    val salesman: String,
    val receivedQty: Int,
)

data class DispatchedItem(
    var product: String?,
    var preAudited: Int = 0,
    var preReturned: Int = 0,
    var preProduced: Int = 0,
    var openingBalance: Int = 0,
    var dispatchList: MutableList<SimpleDispatch> = ArrayList(),
    var dispatchedTotal: Int = 0,
    var countedFactoryDamages: Int = 0,
    var countedBalance: Int = 0,
    var expectedBalance: Int = 0,
    var unaccountedBalance: Int = 0,
){
    fun evaluate() {
        openingBalance = preAudited + preReturned + preProduced
        dispatchedTotal = dispatchList.sumOf { it.receivedQty }
        expectedBalance = openingBalance - dispatchedTotal - countedFactoryDamages
        unaccountedBalance = countedBalance - expectedBalance
    }
}

data class DispatchedBreakDown(
    var factoryReportCard: FactoryReportCard,
) {
    val dispatchedItemsMap: MutableMap<String, DispatchedItem> = TreeMap()

    init {
        factoryReportCard.preAuditedList.forEach { invoice ->
            invoice.items.forEach { product ->
                val productName = product.product_name
                if (dispatchedItemsMap.containsKey(productName)){
                    dispatchedItemsMap[productName]?.apply {
                        preAudited += product.qty
                    }
                }else{
                    val newItem = DispatchedItem(productName, preAudited = product.qty)
                    dispatchedItemsMap.putIfAbsent(productName, newItem)
                }
            }
        }

        factoryReportCard.preReturnedList.forEach { invoice ->
            invoice.items.forEach { product ->
                val productName = product.product_name
                if (dispatchedItemsMap.containsKey(productName)){
                    dispatchedItemsMap[productName]?.apply {
                        preReturned += product.qty
                    }
                }else{
                    val newItem = DispatchedItem(productName, preReturned = product.qty)
                    dispatchedItemsMap.putIfAbsent(productName, newItem)
                }
            }
        }

        factoryReportCard.preProducedList.forEach { invoice ->
            invoice.items.forEach { product ->
                val productName = product.product_name
                if (dispatchedItemsMap.containsKey(productName)){
                    dispatchedItemsMap[productName]?.apply {
                        preProduced += product.produced_qty
                    }
                }else{
                    val newItem = DispatchedItem(productName, preProduced = product.produced_qty)
                    dispatchedItemsMap.putIfAbsent(productName, newItem)
                }
            }
        }

        factoryReportCard.dispatchedList.forEach {  dispatch ->
            dispatch.items.forEach { product ->
                val productName = product.product_name
                val simpleDispatch = SimpleDispatch(dispatch.salesman_name, product.qty)

                if (dispatchedItemsMap.containsKey(productName)){
                    dispatchedItemsMap[productName]?.apply {
                        dispatchList.add(simpleDispatch)
                    }
                }else{
                    val newItem = DispatchedItem(productName)
                    newItem.dispatchList.add(simpleDispatch)
                    dispatchedItemsMap.putIfAbsent(productName, newItem)
                }
            }
        }

        factoryReportCard.auditedList.forEach { invoice ->
            invoice.items.forEach { product ->
                val productName = product.product_name
                if (dispatchedItemsMap.containsKey(productName)){
                    dispatchedItemsMap[productName]?.apply {
                        countedBalance += product.qty
                    }
                }else{
                    val newItem = DispatchedItem(productName, countedBalance = product.qty)
                    dispatchedItemsMap.putIfAbsent(productName, newItem)
                }
            }
        }

        factoryReportCard.expiredList.forEach { invoice ->
            invoice.items.forEach { product ->
                val productName = product.product_name
                if (dispatchedItemsMap.containsKey(productName)){
                    dispatchedItemsMap[productName]?.apply {
                        countedFactoryDamages += product.qty
                    }
                }else{
                    val newItem = DispatchedItem(productName, countedFactoryDamages = product.qty)
                    dispatchedItemsMap.putIfAbsent(productName, newItem)
                }
            }
        }

        dispatchedItemsMap.values.forEach {
            it.evaluate()
        }

    }
}
