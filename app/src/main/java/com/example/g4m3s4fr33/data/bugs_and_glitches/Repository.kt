package com.example.g4m3s4fr33.data.bugs_and_glitches


import com.example.g4m3s4fr33.data.local.CheezzyDatabase
import com.example.g4m3s4fr33.data.model.user.NoLifer

class Repository(private val database: CheezzyDatabase) {

    val user = database.noLiferDao.getUser()

    suspend fun upsertUser(user: NoLifer) {
        database.noLiferDao.upsetUser(user)
    }

    suspend fun updateUserName(name: String) {
        database.noLiferDao.updateUserName(name)
    }

    suspend fun updateUserImage(userImage: String) {
        database.noLiferDao.updateUserImage(userImage)
    }


}