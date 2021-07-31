package com.example.upcyclick.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.upcyclick.database.dao.QuestionDao
import com.example.upcyclick.database.dao.ScrollDao
import com.example.upcyclick.database.entity.Question
import com.example.upcyclick.database.entity.Scroll

@Database(entities = [Scroll::class, Question::class], version = 1)
abstract class UpDB: RoomDatabase() {
    abstract fun scrollDao(): ScrollDao
    abstract fun questionDao(): QuestionDao
}