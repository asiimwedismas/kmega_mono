package me.asiimwedismas.kmega_mono.module_bakery.di

import com.google.firebase.firestore.CollectionReference
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.kmega_mono.module_bakery.data.local.data_source.BakeryDatabase
import me.asiimwedismas.kmega_mono.module_bakery.data.repository.*
import me.asiimwedismas.kmega_mono.module_bakery.domain.repository.*
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

    @Provides
    @Singleton
    fun provideProductionRepository(
        @ProductionCollection collectionReference: CollectionReference,
    ): ProductionRepository = ProductionRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideUsedIngredientsRepository(
        @UsedIngredientsCollection collectionReference: CollectionReference,
    ): UsedIngredientsRepository = UsedIngredientsRepositoryImpl(collectionReference)

    @Provides
    @Singleton
    fun provideDispatchRepository(
        @DispatchedCollection collectionReference: CollectionReference,
    ): DispatchesRepository = DispatchesRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideAgentRepository(
        @AgentDeliveryCollection collectionReference: CollectionReference,
    ): AgentRepository = AgentRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideOutletRepository(
        @OutletDeliveryCollection collectionReference: CollectionReference,
    ): OutletRepository = OutletRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideAuditRepository(
        @AuditCollection collectionReference: CollectionReference,
    ): AuditRepository = AuditRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideExpiredRepository(
        @ExpiredCollection collectionReference: CollectionReference,
    ): ExpiredRepository = ExpiredRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideReturnRepository(
        @ReturnsCollection collectionReference: CollectionReference,
    ): ReturnRepository = ReturnRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideFinancesRepository(
        @SalesmenHandoversCollection salesmenReference: CollectionReference,
        @OutletHandoversCollection outletReference: CollectionReference,
        @SalesmenFieldExpendituresCollection fieldExpendituresReference: CollectionReference,
        @SafeTransactionsCollection safeTransactionsCollection: CollectionReference
    ): FinancesRepository =
        FinancesRepositoryImp(
            salesmenReference,
            outletReference,
            fieldExpendituresReference,
            safeTransactionsCollection
        )
}