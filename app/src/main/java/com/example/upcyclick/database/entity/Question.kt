package com.example.upcyclick.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "questions")
data class Question(
    var difficulty: Int,
    var question: String,
    var answers: String,
    var rightAnswer: String,
    var description: String,
    var imageId: Int
){
    @PrimaryKey(autoGenerate = true) var _id: Int=0
}
