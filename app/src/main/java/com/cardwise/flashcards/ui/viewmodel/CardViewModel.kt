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
class CardViewModel @Inject constructor(
    private val cardRepository: CardRepository
) : ViewModel() {

    // StateFlow для хранения текущей карточки
    private val _currentCard = MutableStateFlow<Card?>(null)
    val currentCard: StateFlow<Card?> = _currentCard

    // StateFlow для хранения сообщений об ошибках
    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        loadRandomCard()
    }

    // Функция загрузки случайной карточки из репозитория
    fun loadRandomCard() {
        viewModelScope.launch {
            try {
                val card = cardRepository.getRandomCard()
                _currentCard.value = card
                _error.value = null // Очистка ошибок при успешной загрузке
            } catch (e: Exception) {
                _error.value = "Failed to load card: ${e.message}"
            }
        }
    }

    /**
     * Обновляет статус текущей карточки как выученной или нет.
     *
     * @param isLearned Boolean, указывающий, выучена ли карточка.
     */
    fun updateCardLearnedStatus(isLearned: Boolean) {
        viewModelScope.launch {
            try {
                _currentCard.value?.let { card ->
                    cardRepository.updateCardLearnedStatus(card.id, isLearned)
                    loadRandomCard()
                }
            } catch (e: Exception) {
                _error.value = "Failed to update card: ${e.message}"
            }
        }
    }
}