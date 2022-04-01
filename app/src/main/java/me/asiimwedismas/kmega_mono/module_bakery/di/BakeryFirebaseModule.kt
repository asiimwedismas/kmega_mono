package me.asiimwedismas.kmega_mono.module_bakery.di

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.kmega_mono.common.Constants.AGENT_DELIVERY_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.AUDIT_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.DISPATCH_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.EXPIRED_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.OUTLET_DELIVERY_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.PRODUCTION_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.RETURNS_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.USED_INGREDIENTS_COLLECTION
import me.asiimwedismas.kmega_mono.common.Constants.v2_BAKERY
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BakeryFirebaseModule {

    @Provides
    @Singleton
    @Named(v2_BAKERY)
    fun provideBakeryDocument(): DocumentReference {
        return Firebase.firestore.collection("v2").document("bakery")
    }

    @Provides
    @Singleton
    @ProductionCollection
    fun provideProductionCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(PRODUCTION_COLLECTION)

    @Provides
    @Singleton
    @UsedIngredientsCollection
    fun provideUsedIngredientsCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(USED_INGREDIENTS_COLLECTION)

    @Provides
    @Singleton
    @DispatchedCollection
    fun provideDispatchCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(DISPATCH_COLLECTION)

    @Provides
    @Singleton
    @OutletDeliveryCollection
    fun provideOutletDeliveryCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(OUTLET_DELIVERY_COLLECTION)

    @Provides
    @Singleton
    @AgentDeliveryCollection
    fun provideAgentDeliveryCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(AGENT_DELIVERY_COLLECTION)

    @Provides
    @Singleton
    @ReturnsCollection
    fun provideReturnsCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(RETURNS_COLLECTION)

    @Provides
    @Singleton
    @ExpiredCollection
    fun provideExpiredCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(EXPIRED_COLLECTION)

    @Provides
    @Singleton
    @AuditCollection
    fun provideAuditCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection(AUDIT_COLLECTION)

    @Provides
    @Singleton
    @SalesmenHandoversCollection
    fun provideSalesmenHandoversCollection(

    ) = Firebase.firestore
        .collection("ACCOUNTING")
        .document("BAKERY")
        .collection("HANDOVERS")

    @Provides
    @Singleton
    @OutletHandoversCollection
    fun provideOutletHandoversCollection(

    ) = Firebase.firestore
        .collection("ACCOUNTING")
        .document("BAKERY")
        .collection("OUTLET_STANDINGS")

    @Provides
    @Singleton
    @SalesmenFieldExpendituresCollection
    fun provideSalesmenFieldExpendituresCollection(

    ) = Firebase.firestore
        .collection("DEPARTMENTS")
        .document("BAKERY")
        .collection("FIELD_EXPENDITURES")
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class SalesmenFieldExpendituresCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OutletHandoversCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class SalesmenHandoversCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ProductionCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class DispatchedCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class OutletDeliveryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AgentDeliveryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ExpiredCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class ReturnsCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class AuditCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class UsedIngredientsCollection