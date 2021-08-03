package com.example.upcyclick.database.dao

import androidx.room.*
import com.example.upcyclick.database.entity.Question

@Dao
interface QuestionDao {
    //Здесь находятся все запросы для этой сущности (можно добавлять свои сколько угодно)
    @Insert
    fun insert(vararg question: Question)

    @Update
    fun update(question: Question)

    @Delete
    fun delete(question: Question)

    @Query("SELECT * FROM questions")
    fun getAll(): List<Question>

    @Query("DELETE FROM questions")
    fun deleteAll()
}