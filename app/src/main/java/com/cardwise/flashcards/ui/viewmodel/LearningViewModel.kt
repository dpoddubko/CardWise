package com.cardwise.flashcards.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cardwise.flashcards.domain.model.Card
import com.cardwise.flashcards.domain.repository.CardRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : ViewModel() {

    // StateFlow для хранения списка несучетенных карточек
    private val _unlearnedCards = MutableStateFlow<List<Card>>(emptyList())
    val unlearnedCards: StateFlow<List<Card>> = _unlearnedCards

    // StateFlow для хранения сообщений об ошибках
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadUnlearnedCards()
    }

    // Функция загрузки несучетенных карточек из репозитория
    private fun loadUnlearnedCards() {
        viewModelScope.launch {
            try {
                val cards = cardRepository.getUnlearnedCards()
                _unlearnedCards.value = cards
                _error.value = null // Очистка ошибок при успешной загрузке
            } catch (e: Exception) {
                _error.value = "Failed to load cards: ${e.message}"
            }
        }
    }

    /**
     * Отмечает карточку как выученную или не выученную.
     *
     * @param card Карточка для обновления.
     * @param isLearned Boolean, указывающий, выучена ли карточка.
     */
    fun markCardAsLearned(card: Card, isLearned: Boolean) {
        viewModelScope.launch {
            try {
                cardRepository.updateCardLearnedStatus(card.id, isLearned)
                // Перезагрузка несучетенных карточек после обновления
                loadUnlearnedCards()
            } catch (e: Exception) {
                _error.value = "Failed to update card: ${e.message}"
            }
        }
    }

    /**
     * Обновляет список несучетенных карточек.
     */
    fun refreshCards() {
        loadUnlearnedCards()
    }
}