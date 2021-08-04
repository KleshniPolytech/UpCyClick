package com.example.upcyclick.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "scrolls")
data class Scroll(
    @PrimaryKey var name: String,
    var filePath: String,
    var purchased: Boolean,
    var description: String,
    var typeId: Int
)