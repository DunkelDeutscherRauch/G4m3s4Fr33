package com.example.g4m3s4fr33

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.g4m3s4fr33.data.bugs_and_glitches.repo.UserRepository
import com.example.g4m3s4fr33.data.bugs_and_glitches.local.CheezzyDatabase
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApi
import com.example.g4m3s4fr33.data.bugs_and_glitches.repo.SenpaiRepository
import com.example.g4m3s4fr33.data.model.user.NoLifer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class WaifuViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = UserRepository(CheezzyDatabase.getDatabase(application))
    private val senpaiRepository = SenpaiRepository(FreeTwoPlayMMOApi)

    val user = userRepository.user
    val gameList = senpaiRepository.gameList

    init {
        user.observeForever {
           if (it == null) {
               upsertUser(NoLifer())
           }
        }
    }

    private fun upsertUser(user: NoLifer) {
        viewModelScope.launch {
            userRepository.upsertUser(user)
        }
    }

    fun updateUserName(name: String) {
        viewModelScope.launch {
            userRepository.updateUserName(name)
        }
    }

    fun updateUserImage(userImage: String) {
        viewModelScope.launch {
            userRepository.updateUserImage(userImage)
        }
    }

    fun getGameList() {
        viewModelScope.launch(Dispatchers.IO) {
            senpaiRepository.getGameList()
        }
    }




}