package me.asiimwedismas.kmega_mono.module_bakery.domain.use_case

import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.ingredient.InsertIngredients
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product.InsertProducts
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.product_ingredient.InsertProductIngredients
import javax.inject.Inject

class BDsynUseCase @Inject constructor(
    val insertIngredients: InsertIngredients,
    val insertProducts: InsertProducts,
    val insertProductIngredients: InsertProductIngredients,
) {
    suspend operator fun invoke() {

        /*
        *  hard coded collections, don't want to go throught the trouble of implementing di
        *  for them as i wont be using them in the production build
        * */

        val ingredsColl = Firebase.firestore
            .collection("DEPARTMENTS").document("BAKERY")
            .collection("INGREDIENTS")
        val prodsColl = Firebase.firestore
            .collection("DEPARTMENTS").document("BAKERY")
            .collection("PRODUCTS")
        val prodIngreColl = Firebase.firestore
            .collection("DEPARTMENTS").document("BAKERY")
            .collection("PRODUCT_INGREDIENTS")

        withContext(Dispatchers.IO) {
            val ingredients = async {
                ingredsColl.get().await().toObjects(BakeryIngredient::class.java)
            }
            val products = async {
                prodsColl.get().await().toObjects(BakeryProduct::class.java)
            }
            val productIngredients = async {
                prodIngreColl.get().await().toObjects(BakeryProductIngredient::class.java)
            }

            insertIngredients(ingredients.await())
            insertProducts(products.await())
            insertProductIngredients(productIngredients.await())
        }
    }
}