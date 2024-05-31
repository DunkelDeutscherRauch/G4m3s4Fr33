package com.example.g4m3s4fr33.data.bugs_and_glitches.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.g4m3s4fr33.data.bugs_and_glitches.local.CheezzyDatabase
import com.example.g4m3s4fr33.data.model.user.NoLifer
import com.example.g4m3s4fr33.data.model.user.RageQuit

class NoLiferRepository(private val database: CheezzyDatabase) {

    val user = database.noLiferDao.getUser()
    val rageQuitList = database.noLiferDao.getFavGames()

    private var _isGameFav = MutableLiveData<Boolean>()
    val isGameFav: LiveData<Boolean>
        get() = _isGameFav

    private var _favGame = MutableLiveData<List<RageQuit>>()
    val favGame: LiveData<List<RageQuit>>
        get() = _favGame

    suspend fun upsertUser(user: NoLifer) {
        database.noLiferDao.upsetUser(user)
    }

    suspend fun updateUserName(name: String) {
        database.noLiferDao.updateUserName(name)
    }

    suspend fun updateUserImage(userImage: String) {
        database.noLiferDao.updateUserImage(userImage)
    }

    suspend fun updateUserAchievement(achievement: Boolean) {
        database.noLiferDao.updateUserAchievement(achievement)
    }

    suspend fun addFavGame(gameId: Int, dateGameAdded: String) {
        database.noLiferDao.addFavGame(gameId, dateGameAdded)
    }

    suspend fun deleteFavGame(gameId: Int) {
        database.noLiferDao.deleteFavGame(gameId)
    }

    suspend fun isSelectedGameFav(gameId: Int) {
        val doIHaveAnArrowInMyKnee = database.noLiferDao.getSelectedFavGame(gameId).isNotEmpty()
        _isGameFav.postValue(doIHaveAnArrowInMyKnee)
    }

    suspend fun getFavGame(gameId: Int) {
        val myFavGame = database.noLiferDao.getSelectedFavGame(gameId)
        _favGame.postValue(myFavGame)
    }

    suspend fun updateHoursPlayed(hoursPlayed: Int, gameId: Int) {
        database.noLiferDao.updateHoursPlayed(hoursPlayed, gameId)
    }

}