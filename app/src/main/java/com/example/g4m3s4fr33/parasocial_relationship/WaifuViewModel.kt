package com.example.g4m3s4fr33.parasocial_relationship

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.g4m3s4fr33.data.bugs_and_glitches.local.CheezzyDatabase
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApi
import com.example.g4m3s4fr33.data.bugs_and_glitches.repo.NoLiferRepository
import com.example.g4m3s4fr33.data.bugs_and_glitches.repo.SenpaiRepository
import com.example.g4m3s4fr33.data.model.user.NoLifer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale


class WaifuViewModel(application: Application) : AndroidViewModel(application) {

    private val noLiferRepository = NoLiferRepository(CheezzyDatabase.getDatabase(application))
    private val senpaiRepository = SenpaiRepository(FreeTwoPlayMMOApi)

    val user = noLiferRepository.user
    val rageQuitList = noLiferRepository.rageQuitList
    val isFavGame = noLiferRepository.isGameFav
    val favGame = noLiferRepository.favGame
    val gameList = senpaiRepository.gameList
    val gameDetail = senpaiRepository.gameDetail
    val gameDetailList = senpaiRepository.gameDetailList

    init {
        user.observeForever {
            if (it == null) {
                upsertUser(NoLifer())
            }
        }
    }

    // local user stuff

    private fun upsertUser(user: NoLifer) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.upsertUser(user)
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.updateUserName(name)
        }
    }

    fun updateUserImage(userImage: String) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.updateUserImage(userImage)
        }
    }

    fun addFavGame(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.addFavGame(gameId, gimmeDate())
            isGameFav(gameId)
        }
    }

    fun deleteFavGame(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.deleteFavGame(gameId)
            isGameFav(gameId)
        }
    }

    fun isGameFav(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.isSelectedGameFav(gameId)
        }
    }

    fun getFavGame(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.getFavGame(gameId)
        }
    }

    // ApiStuff

    fun getGameList() {
        viewModelScope.launch(Dispatchers.IO) {
            senpaiRepository.getGameList()
        }
    }

    fun getGameDetail(gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            senpaiRepository.getGameDetail(gameId)
        }
    }

    fun getGameDetailList(gameIdList: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            senpaiRepository.getGameDetailList(gameIdList)
        }

    }

    // other stuff

    private fun gimmeDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = dateFormat.format(calendar.time)

        return date.toString()
    }


}