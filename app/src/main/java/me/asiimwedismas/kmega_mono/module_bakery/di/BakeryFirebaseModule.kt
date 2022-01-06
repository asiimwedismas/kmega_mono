package me.asiimwedismas.kmega_mono.module_bakery.di

import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.bakery_module.other.Constants.AGENT_DELIVERY_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.BAKERY_DOCUMENT
import me.asiimwedismas.bakery_module.other.Constants.DISPATCH_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.EXPIRED_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.FACTORY_AUDIT_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.OUTLET_AUDIT_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.OUTLET_DELIVERY_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.PRODUCTION_COLLECTION
import me.asiimwedismas.bakery_module.other.Constants.RETURNS_COLLECTION
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object BakeryFirebaseModule {

    @Provides
    @Singleton
    @Named(BAKERY_DOCUMENT)
    fun provideBakeryDocument(): DocumentReference {
        return Firebase.firestore.collection("v2").document("bakery")
    }

    @Provides
    @Singleton
    @Named(PRODUCTION_COLLECTION)
    fun provideProductionCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("production")

    @Provides
    @Singleton
    @Named(DISPATCH_COLLECTION)
    fun provideDispatchCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("dispatch")

    @Provides
    @Singleton
    @Named(OUTLET_DELIVERY_COLLECTION)
    fun provideOutletDeliveryCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("outlet_delivery")

    @Provides
    @Singleton
    @Named(AGENT_DELIVERY_COLLECTION)
    fun provideAgentDeliveryCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("agent_delivery")

    @Provides
    @Singleton
    @Named(RETURNS_COLLECTION)
    fun provideReturnsCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("returns")

    @Provides
    @Singleton
    @Named(EXPIRED_COLLECTION)
    fun provideExpiredCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("expired")

    @Provides
    @Singleton
    @Named(OUTLET_AUDIT_COLLECTION)
    fun provideOutletAuditCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("outlet_audit")

    @Provides
    @Singleton
    @Named(FACTORY_AUDIT_COLLECTION)
    fun provideFactoryAuditCollection(
        @Named(BAKERY_DOCUMENT) bakeryDocument: DocumentReference
    ) = bakeryDocument.collection("factory_audit")
}