package com.cardwise.flashcards.di

import android.content.Context
import androidx.room.Room
import com.cardwise.flashcards.data.local.AppDatabase
import com.cardwise.flashcards.data.local.dao.CardDao
import com.cardwise.flashcards.data.repository.CardRepositoryImpl
import com.cardwise.flashcards.domain.repository.CardRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "flashcards_database"
        ).build()
    }

    @Provides
    fun provideCardDao(database: AppDatabase): CardDao {
        return database.cardDao()
    }

    @Provides
    @Singleton
    fun provideCardRepository(cardDao: CardDao): CardRepository {
        return CardRepositoryImpl(cardDao)
    }
}
