package com.example.g4m3s4fr33.data.bugs_and_glitches.repo

import android.util.Log
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

    // TODO guess i need it

    private var _gameDetailList = MutableLiveData<List<SixteenTimesTheDetail>>()
    val gameDetailList: LiveData<List<SixteenTimesTheDetail>>
        get() = _gameDetailList

    suspend fun getGameList() {
        try {
            val result = mmoApi.retrofitService.getGameList()
            _gameList.postValue(result)
            Log.i("Ωlul", "Me gez gamez!")
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
        }
    }

    suspend fun getGameDetail(gameId: Int) {
        try {
            val result = mmoApi.retrofitService.getGameDetail(gameId)
            _gameDetail.postValue(result)
            Log.i("Ωlul", "Me gez detailz!")
        } catch (e: Exception) {
            Log.e("Ωlul", "$e")
        }
    }

    suspend fun getGameDetailList(gameIdList: List<Int>): List<SixteenTimesTheDetail> {
        val gameDetailList = mutableListOf<SixteenTimesTheDetail>()

        for (gameId in gameIdList) {
            try {
                val result = mmoApi.retrofitService.getGameDetail(gameId)
                gameDetailList.add(result)
                Log.i("Ωlul","SenpaiRepository givz me data: $result")
            } catch (e: Exception) {
                Log.e("Ωlul","$e")
            }
        }
        Log.i("Ωlul", "$gameDetailList")
        return gameDetailList

    }

}

