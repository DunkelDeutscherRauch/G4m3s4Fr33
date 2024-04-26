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


class WaifuViewModel(application: Application) : AndroidViewModel(application) {

    private val noLiferRepository = NoLiferRepository(CheezzyDatabase.getDatabase(application))
    private val senpaiRepository = SenpaiRepository(FreeTwoPlayMMOApi)

    val user = noLiferRepository.user
    val rageQuitList = noLiferRepository.rageQuitList
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
            noLiferRepository.addFavGame(gameId)
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


}