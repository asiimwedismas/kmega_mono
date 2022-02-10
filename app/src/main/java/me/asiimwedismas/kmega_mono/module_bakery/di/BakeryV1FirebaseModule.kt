package me.asiimwedismas.kmega_mono.module_bakery.di

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.kmega_mono.common.Constants
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object BakeryV1FirebaseModule {

    @Provides
    @Singleton
    @Named(Constants.v1_BAKERY)
    fun provideV1BakeryDocument(): DocumentReference {
        return Firebase.firestore.collection("DEPARTMENTS")
            .document("BAKERY")
    }

    @Provides
    @Singleton
    @V1Production
    fun provideProductionCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("FACTORY_PRODUCTION")


    @Provides
    @Singleton
    @V1DispatchCollection
    fun provideDispatchCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("FACTORY_DISPACTH")

    @Provides
    @Singleton
    @V1OutletDeliveryCollection
    fun provideOutletDeliveryCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("OUTLET_DELIVERY")

    @Provides
    @Singleton
    @V1AgentDeliveryCollection
    fun provideAgentDeliveryCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("AGENT_DELIVERY")

    @Provides
    @Singleton
    @V1ReturnsCollection
    fun provideReturnsCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("BAKERY_RETURN")

    @Provides
    @Singleton
    @V1ExpiredCollection
    fun provideExpiredCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("BAKERY_EXPIRED")

    @Provides
    @Singleton
    @V1AuditCollection
    fun provideAuditCollection(
        @Named(Constants.v1_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("STOCK_AUDIT_OUTLET")
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1Production

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1DispatchCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1OutletDeliveryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1AgentDeliveryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1ExpiredCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1ReturnsCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V1AuditCollection