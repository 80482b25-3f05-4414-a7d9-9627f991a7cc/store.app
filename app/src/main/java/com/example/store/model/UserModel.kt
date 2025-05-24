package com.example.store.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserModel
(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val username: String,
    var password: String,
    val fullName: String,
    val role: String
)
