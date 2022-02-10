package me.asiimwedismas.kmega_mono.module_bakery.di

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.kmega_mono.common.Constants.v2_BAKERY
import javax.inject.Named
import javax.inject.Qualifier
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BakeryV2FirebaseModule {

    @Provides
    @Singleton
    @Named(v2_BAKERY)
    fun provideV2BakeryDocument(): DocumentReference {
        return Firebase.firestore.collection("v2").document("bakery")
    }

    @Provides
    @Singleton
    @V2Production
    fun provideProductionCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("production")

    @Provides
    @Singleton
    @V2DispatchCollection
    fun provideDispatchCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("dispatch")

    @Provides
    @Singleton
    @V2OutletDeliveryCollection
    fun provideOutletDeliveryCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("outlet_delivery")

    @Provides
    @Singleton
    @V2AgentDeliveryCollection
    fun provideAgentDeliveryCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("agent_delivery")

    @Provides
    @Singleton
    @V2ReturnsCollection
    fun provideReturnsCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("returns")

    @Provides
    @Singleton
    @V2ExpiredCollection
    fun provideExpiredCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("expired")

    @Provides
    @Singleton
    @V2AuditCollection
    fun provideAuditCollection(
        @Named(v2_BAKERY) bakeryDocument: DocumentReference,
    ) = bakeryDocument.collection("audit")
}

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2Production

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2DispatchCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2OutletDeliveryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2AgentDeliveryCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2ExpiredCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2ReturnsCollection

@Retention(AnnotationRetention.BINARY)
@Qualifier
annotation class V2AuditCollection