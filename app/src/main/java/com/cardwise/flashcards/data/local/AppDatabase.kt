package com.cardwise.flashcards.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.cardwise.flashcards.data.local.dao.CardDao
import com.cardwise.flashcards.data.local.entity.CardEntity

@Database(entities = [CardEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cardDao(): CardDao
}
