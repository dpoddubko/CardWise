package com.cardwise.flashcards.data.mapper

import com.cardwise.flashcards.data.local.entity.CardEntity
import com.cardwise.flashcards.domain.model.Card

fun CardEntity.toDomainModel() = Card(
    id = id,
    foreignWord = foreignWord,
    translation = translation,
    languageId = languageId,
    isLearned = isLearned
)

fun Card.toEntity() = CardEntity(
    id = id,
    foreignWord = foreignWord,
    translation = translation,
    languageId = languageId,
    isLearned = isLearned
)
