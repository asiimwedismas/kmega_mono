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
        db: BakeryDatabase
    ): IngredientRepository = IngredientRepositoryImp(db.ingredientDao())

    @Provides
    @Singleton
    fun provideProductionRepository(
        @V1Production  collectionReference: CollectionReference
    ): ProductionRepository = ProductionRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideDispatchRepository(
        @V1DispatchCollection  collectionReference: CollectionReference
    ): DispatchesRepository = DispatchesRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideAgentRepository(
        @V1AgentDeliveryCollection collectionReference: CollectionReference
    ): AgentRepository = AgentRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideOutletRepository(
        @V1OutletDeliveryCollection collectionReference: CollectionReference
    ): OutletRepository = OutletRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideAuditRepository(
        @V1AuditCollection collectionReference: CollectionReference
    ): AuditRepository = AuditRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideExpiredRepository(
        @V1ExpiredCollection collectionReference: CollectionReference
    ): ExpiredRepository = ExpiredRepositoryImp(collectionReference)

    @Provides
    @Singleton
    fun provideReturnRepository(
        @V1ReturnsCollection collectionReference: CollectionReference
    ): ReturnRepository = ReturnRepositoryImp(collectionReference)
}