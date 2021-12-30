package me.asiimwedismas.kmega_mono.module_bakery.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository
import me.asiimwedismas.kmega_mono.module_bakery.data.repository.IngredientRepositoryImp
import me.asiimwedismas.kmega_mono.module_bakery.data.repository.ProductIngredientRepositoryImp
import me.asiimwedismas.kmega_mono.module_bakery.data.repository.ProductRepositoryImp
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideProductRepository(
        db: BakeryDatabase,
    ): ProductRepository = ProductRepositoryImp(db.productDao())

    @Provides
    @Singleton
    fun provideProductIngredientRepository(
        db: BakeryDatabase,
    ): ProductIngredientRepository = ProductIngredientRepositoryImp(db.productIngredientDao())

    @Provides
    @Singleton
    fun provideIngredientRepository(
        db: BakeryDatabase,
    ): IngredientRepository = IngredientRepositoryImp(db.ingredientDao())
}