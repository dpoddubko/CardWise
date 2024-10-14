package com.cardwise.flashcards.data.repository

import com.cardwise.flashcards.data.local.dao.CardDao
import com.cardwise.flashcards.data.mapper.toDomainModel
import com.cardwise.flashcards.data.mapper.toEntity
import com.cardwise.flashcards.domain.model.Card
import com.cardwise.flashcards.domain.repository.CardRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CardRepositoryImpl @Inject constructor(
    private val cardDao: CardDao
) : CardRepository {

    override suspend fun getCards(languageId: Int): List<Card> = withContext(Dispatchers.IO) {
        cardDao.getCardsByLanguage(languageId).map { it.toDomainModel() }
    }

    override suspend fun getUnlearnedCards(languageId: Int): List<Card> = withContext(Dispatchers.IO) {
        cardDao.getUnlearnedCardsByLanguage(languageId).map { it.toDomainModel() }
    }

    override suspend fun addCard(card: Card) = withContext(Dispatchers.IO) {
        cardDao.insertCard(card.toEntity())
    }

    override suspend fun updateCard(card: Card) = withContext(Dispatchers.IO) {
        cardDao.updateCard(card.toEntity())
    }

    override suspend fun deleteCard(card: Card) = withContext(Dispatchers.IO) {
        cardDao.deleteCard(card.toEntity())
    }
}