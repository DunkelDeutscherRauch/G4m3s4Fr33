package com.example.g4m3s4fr33.data.bugs_and_glitches.repo

import android.content.Context
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.g4m3s4fr33.data.bugs_and_glitches.remote.FreeTwoPlayMMOApi
import com.example.g4m3s4fr33.data.model.gamingstuff.IWantToPlayUnrealTournament
import com.example.g4m3s4fr33.data.model.gamingstuff.SixteenTimesTheDetail

class SenpaiRepository(private val mmoApi: FreeTwoPlayMMOApi) {

    private var _gameList = MutableLiveData<List<IWantToPlayUnrealTournament>>()
    val gameList: LiveData<List<IWantToPlayUnrealTournament>>
        get() = _gameList

    private var _gameDetail = MutableLiveData<SixteenTimesTheDetail>()
    val gameDetail: LiveData<SixteenTimesTheDetail>
        get() = _gameDetail

    private var _gameDetailList = MutableLiveData<List<SixteenTimesTheDetail>>()
    val gameDetailList: LiveData<List<SixteenTimesTheDetail>>
        get() = _gameDetailList

    private var _fourOFour = MutableLiveData<String>()
    val fourOFour: LiveData<String>
        get() = _fourOFour

    suspend fun getGameList() {
        try {
            val result = mmoApi.retrofitService.getGameList()
            _gameList.postValue(result)
            Log.i("Ωlul", "Me gez gamez!")
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
            _fourOFour.postValue("")
        }
    }

    suspend fun getGameListByFilter(platform: String, category: String, sortBy: String) {
        try {
            val result = mmoApi.retrofitService.getGameListByFilter(platform, category, sortBy)
            _gameList.postValue(result)
            Log.i("Ωlul", "Me gez gamez by $platform, $category and $sortBy!")
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
            _fourOFour.postValue("")
        }
    }

    suspend fun getGameDetail(gameId: Int) {
        try {
            val result = mmoApi.retrofitService.getGameDetail(gameId)
            _gameDetail.postValue(result)
            Log.i("Ωlul", "Me gez detailz!")
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
            _fourOFour.postValue("")
        }
    }

    suspend fun getGameDetailList(gameIdList: List<Int>) {
        val gameDetailList = mutableListOf<SixteenTimesTheDetail>()

        for (gameId in gameIdList) {
            try {
                val result = mmoApi.retrofitService.getGameDetail(gameId)
                gameDetailList.add(result)
                Log.i("Ωlul", "SenpaiRepository givz me data: $result")
            } catch (e: Exception) {
                Log.e("Ωlul", "$e")
                _fourOFour.postValue("")
            }
        }
        _gameDetailList.postValue(gameDetailList)
    }

}

