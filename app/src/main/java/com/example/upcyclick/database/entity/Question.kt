package com.example.upcyclick.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    @PrimaryKey(autoGenerate = true) var _id: Int,
    var question: String,
    var answers: String,
    var rightAnswerIndex: Int,
    var description: String
)