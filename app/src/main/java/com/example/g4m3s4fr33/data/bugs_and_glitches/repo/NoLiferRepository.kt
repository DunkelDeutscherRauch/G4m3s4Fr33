package com.example.g4m3s4fr33.data.bugs_and_glitches.repo


import com.example.g4m3s4fr33.data.bugs_and_glitches.local.CheezzyDatabase
import com.example.g4m3s4fr33.data.model.user.NoLifer

class NoLiferRepository(private val database: CheezzyDatabase) {

    val user = database.noLiferDao.getUser()
    val rageQuitList = database.noLiferDao.getFavGames()

    suspend fun upsertUser(user: NoLifer) {
        database.noLiferDao.upsetUser(user)
    }

    suspend fun updateUserName(name: String) {
        database.noLiferDao.updateUserName(name)
    }

    suspend fun updateUserImage(userImage: String) {
        database.noLiferDao.updateUserImage(userImage)
    }

    suspend fun addFavGame(gameId: Int) {
        database.noLiferDao.addFavGame(gameId)

    }


}