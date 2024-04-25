package com.example.g4m3s4fr33.data.model.user

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("rage_quit_table")
data class RageQuit(
    @PrimaryKey
    val gameId: Int,
    val userId: Int
)
