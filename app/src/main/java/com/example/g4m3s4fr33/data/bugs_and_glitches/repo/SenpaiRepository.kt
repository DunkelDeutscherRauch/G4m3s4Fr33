package com.example.g4m3s4fr33.data.bugs_and_glitches.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApi
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApiService
import com.example.g4m3s4fr33.data.model.gamingstuff.IWantToPlayUnrealTournament

class SenpaiRepository(private val mmoApi: FreeTwoPlayMMOApi) {

    private var _gamesList = MutableLiveData<List<IWantToPlayUnrealTournament>>()
    val gamesList: LiveData<List<IWantToPlayUnrealTournament>>
        get() = _gamesList

    suspend fun getGameList() {
        try {
            val result = mmoApi.retrofitService.getGameList()
            _gamesList.postValue(result)
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
        }
    }

}