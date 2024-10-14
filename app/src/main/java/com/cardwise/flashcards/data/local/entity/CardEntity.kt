package com.cardwise.flashcards.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cards")
data class CardEntity(
    @PrimaryKey(autoGenerate = true) val id: Int? = null,
    val foreignWord: String,
    val translation: String,
    val languageId: Int,
    val isLearned: Boolean
)
