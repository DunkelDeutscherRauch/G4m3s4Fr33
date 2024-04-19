package com.example.g4m3s4fr33.data.bugs_and_glitches.repo

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApi
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApiService
import com.example.g4m3s4fr33.data.model.gamingstuff.IWantToPlayUnrealTournament

class SenpaiRepository(private val mmoApi: FreeTwoPlayMMOApi) {

    private var _gameList = MutableLiveData<List<IWantToPlayUnrealTournament>>()
    val gameList: LiveData<List<IWantToPlayUnrealTournament>>
        get() = _gameList

    suspend fun getGameList() {
        try {
            val result = mmoApi.retrofitService.getGameList()
            _gameList.postValue(result)
            Log.i("Ωlul","It works")
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
        }
    }

}