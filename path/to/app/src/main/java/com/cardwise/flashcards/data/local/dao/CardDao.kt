package com.cardwise.flashcards.data.local.dao

import androidx.room.*
import com.cardwise.flashcards.data.local.entity.CardEntity

@Dao
interface CardDao {
    @Query("SELECT * FROM cards WHERE languageId = :languageId")
    suspend fun getCardsByLanguage(languageId: Int): List<CardEntity>

    @Query("SELECT * FROM cards WHERE languageId = :languageId AND isLearned = 0")
    suspend fun getUnlearnedCardsByLanguage(languageId: Int): List<CardEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCard(card: CardEntity): Long

    @Update
    suspend fun updateCard(card: CardEntity)

    @Delete
    suspend fun deleteCard(card: CardEntity)
}