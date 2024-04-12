package com.example.g4m3s4fr33.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "kek_table")
data class NoLifer(
    @PrimaryKey(autoGenerate = true)
    val idn: Int = 0,
    val name: String = "User",
    val userImage: String = ""
)
