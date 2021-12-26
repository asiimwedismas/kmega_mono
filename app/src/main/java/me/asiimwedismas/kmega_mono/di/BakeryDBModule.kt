package me.asiimwedismas.kmega_mono.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.bakery_module.data.local.data_source.BakeryDatabase
import me.asiimwedismas.bakery_module.other.Constants.LOCAL_DB
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BakeryDBModule {

    @Singleton
    @Provides
    fun provideBakeryDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, BakeryDatabase::class.java, LOCAL_DB).build()

    @Singleton
    @Provides
    fun provideIngredientsDao(
        database: BakeryDatabase
    ) = database.ingredientDao()

    @Singleton
    @Provides
    fun provideProductsDao(
        database: BakeryDatabase
    ) = database.productDao()

    @Singleton
    @Provides
    fun provideProductIngredientsDao(
        database: BakeryDatabase
    ) = database.productIngredientDao()
}