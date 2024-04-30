package com.example.g4m3s4fr33.data.bugs_and_glitches.repo


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.g4m3s4fr33.data.bugs_and_glitches.local.CheezzyDatabase
import com.example.g4m3s4fr33.data.model.user.NoLifer

class NoLiferRepository(private val database: CheezzyDatabase) {

    val user = database.noLiferDao.getUser()
    val rageQuitList = database.noLiferDao.getFavGames()

    private var _isGameFav = MutableLiveData<Boolean>()
    val isGameFav: LiveData<Boolean>
        get() = _isGameFav

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

    suspend fun deleteFavGame(gameId: Int) {
        database.noLiferDao.deleteFavGame(gameId)
    }

    suspend fun isSelectedGameFav(gameId: Int) {
        val myContance = database.noLiferDao.getSelectedFavGame(gameId).isNotEmpty()
        _isGameFav.postValue(myContance)
    }

}