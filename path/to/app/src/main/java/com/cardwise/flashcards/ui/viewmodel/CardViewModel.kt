package com.cardwise.flashcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardwise.flashcards.domain.model.Card
import com.cardwise.flashcards.domain.repository.CardRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CardViewModel @Inject constructor(
    private val repository: CardRepository
) : ViewModel() {

    private val _cards = MutableStateFlow<List<Card>>(emptyList())
    val cards: StateFlow<List<Card>> = _cards.asStateFlow()

    private val _unlearnedCards = MutableStateFlow<List<Card>>(emptyList())
    val unlearnedCards: StateFlow<List<Card>> = _unlearnedCards.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun loadCards(languageId: Int) {
        viewModelScope.launch {
            try {
                _cards.value = repository.getCards(languageId)
            } catch (e: Exception) {
                _error.value = "Не удалось загрузить карточки: ${e.message}"
            }
        }
    }

    fun loadUnlearnedCards(languageId: Int) {
        viewModelScope.launch {
            try {
                _unlearnedCards.value = repository.getUnlearnedCards(languageId)
            } catch (e: Exception) {
                _error.value = "Не удалось загрузить невыученные карточки: ${e.message}"
            }
        }
    }

    fun addCard(foreignWord: String, translation: String, languageId: Int) {
        viewModelScope.launch {
            try {
                val newCard = Card(
                    foreignWord = foreignWord,
                    translation = translation,
                    languageId = languageId,
                    isLearned = false
                )
                repository.addCard(newCard)
                // Обновляем список карточек после добавления
                loadCards(languageId)
            } catch (e: Exception) {
                _error.value = "Не удалось добавить карточку: ${e.message}"
            }
        }
    }

    fun markCardAsLearned(card: Card, isLearned: Boolean) {
        viewModelScope.launch {
            try {
                val updatedCard = card.copy(isLearned = isLearned)
                repository.updateCard(updatedCard)
                // Обновляем списки после изменения
                loadCards(card.languageId)
                loadUnlearnedCards(card.languageId)
            } catch (e: Exception) {
                _error.value = "Не удалось обновить карточку: ${e.message}"
            }
        }
    }

    fun updateCard(card: Card) {
        viewModelScope.launch {
            try {
                repository.updateCard(card)
                loadCards(card.languageId)
            } catch (e: Exception) {
                _error.value = "Не удалось обновить карточку: ${e.message}"
            }
        }
    }

    fun deleteCard(card: Card) {
        viewModelScope.launch {
            try {
                repository.deleteCard(card)
                loadCards(card.languageId)
            } catch (e: Exception) {
                _error.value = "Не удалось удалить карточку: ${e.message}"
            }
        }
    }
}