package me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source

import androidx.room.Database
import androidx.room.RoomDatabase
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryIngredient
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProduct
import me.asiimwedismas.kmega_mono.module_bakery.domain.model.BakeryProductIngredient

@Database(
    entities = [BakeryProduct::class, BakeryProductIngredient::class, BakeryIngredient::class],
    version = 1
)
abstract class BakeryDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun productIngredientDao(): ProductIngredientDao
    abstract fun ingredientDao(): IngredientDao
}