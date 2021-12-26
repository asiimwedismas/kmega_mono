package me.asiimwedismas.kmega_mono.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import me.asiimwedismas.bakery_module.data.local.data_source.BakeryDatabase
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object TestModule {

    @Provides
    @Named("test_db")
    fun provideInMemoryDatabase(
        @ApplicationContext context: Context
    ) = Room.inMemoryDatabaseBuilder(
        context,
        BakeryDatabase::class.java
    ).allowMainThreadQueries().build()
}