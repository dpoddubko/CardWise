package com.cardwise.flashcards.domain.repository

import com.cardwise.flashcards.domain.model.Card

interface CardRepository {
    suspend fun getCards(languageId: Int): List<Card>
    suspend fun getUnlearnedCards(languageId: Int): List<Card>
    suspend fun addCard(card: Card)
    suspend fun updateCard(card: Card)
    suspend fun deleteCard(card: Card)
}