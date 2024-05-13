package com.example.g4m3s4fr33.parasocial_relationship

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.g4m3s4fr33.R
import com.example.g4m3s4fr33.data.bugs_and_glitches.local.CheezzyDatabase.Companion.getDatabase
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

    private val database = getDatabase(application)
    private val noLiferRepository = NoLiferRepository(database)
    private val senpaiRepository = SenpaiRepository(FreeTwoPlayMMOApi)

    val user = noLiferRepository.user
    val rageQuitList = noLiferRepository.rageQuitList
    val isFavGame = noLiferRepository.isGameFav
    val favGame = noLiferRepository.favGame
    val gameList = senpaiRepository.gameList
    val gameDetail = senpaiRepository.gameDetail
    val gameDetailList = senpaiRepository.gameDetailList

    var platform = "all"
    var category = ""
    var sortBy = "relevance"

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

    fun updateHoursPlayed(hoursPlayed: Int, gameId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            noLiferRepository.updateHoursPlayed(hoursPlayed, gameId)
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

    fun getGameListByFilter() {
        viewModelScope.launch(Dispatchers.IO) {
            senpaiRepository.getGameListByFilter(platform, category, sortBy)
            Log.i("Ωlul", "ViewModel: $platform, $category, $sortBy")
        }

    }

    fun getRandomGame(randomCategory: String) {
        viewModelScope.launch(Dispatchers.IO) {
            senpaiRepository.getGameListByFilter("all", randomCategory, "relevance")
        }
    }

    // other stuff

    private fun gimmeDate(): String {
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val date = dateFormat.format(calendar.time)

        return date.toString()
    }

    fun gimmeRank(playtime: Int): Int {
       return when(playtime) {

           0 -> {
                R.string.unranked
            }

           in 1..24 -> {
               R.string.rank_noob
           }

           in 25..49 -> {
               R.string.rank_rookie
           }

           in 50..99 -> {
               R.string.rank_causal_gamer
           }

           in 100..249 -> {
               R.string.rank_pro_Gamer
           }

           // TODO more to come here :D

           else -> {
               R.string.unranked
           }
       }
    }

}