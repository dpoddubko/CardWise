package com.cardwise.flashcards.domain.model

data class Card(
    val id: Int? = null,
    val foreignWord: String,
    val translation: String,
    val languageId: Int,
    val isLearned: Boolean = false
)

