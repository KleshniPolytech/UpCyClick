package com.example.upcyclick.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "upgrades")
data class Upgrade(
    @PrimaryKey var name: String,
    var purchased: Boolean,
    var income: Int
)