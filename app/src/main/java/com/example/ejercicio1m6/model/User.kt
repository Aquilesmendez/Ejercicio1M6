package com.example.ejercicio1m6.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class User(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val gameName: String,
    val fullName: String,
    val age: Int
)


