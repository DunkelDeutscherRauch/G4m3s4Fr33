package com.example.g4m3s4fr33.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kek_table")
data class NoLifer(
    @PrimaryKey
    val id: Int = 0,
    val name: String = "",
    val userImage: String = "",
)
