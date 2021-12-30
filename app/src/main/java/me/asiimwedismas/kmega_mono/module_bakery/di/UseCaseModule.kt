package me.asiimwedismas.kmega_mono.module_bakery.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.IngredientRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductIngredientRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.ProductRepository
import me.asiimwedismas.kmega_mono.module_bakery.domain.use_case.*
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideProductUseCases(
        repository: ProductRepository,
    ): ProductUseCases {
        return ProductUseCases(
            insertProducts = InsertProducts(repository),
            updateProducts = UpdateProducts(repository),
            deleteProducts = DeleteProducts(repository),
            getAllProducts = GetAllProducts(repository),
            getProduct = GetProduct(repository),
        )
    }

    @Provides
    @Singleton
    fun provideIngredientUseCases(
        repository: IngredientRepository,
    ): IngredientUseCases {
        return IngredientUseCases(
            insertIngredients = InsertIngredients(repository),
            updateIngredients = UpdateIngredients(repository),
            deleteIngredients = DeleteIngredients(repository),
            getAllIngredients = GetAllIngredients(repository),
        )
    }

    @Provides
    @Singleton
    fun provideProductIngredientUseCases(
        repository: ProductIngredientRepository,
    ): ProductIngredientUseCases {
        return ProductIngredientUseCases(
            insert = Insert(repository),
            update = Update(repository),
            delete = Delete(repository),
            getAllProductIngredients = GetAllProductIngredients(repository),
            getRowsWithIngredient = GetRowsWithIngredient(repository),
            getIngredientsForProduct = GetIngredientsForProduct(repository)
        )
    }
}