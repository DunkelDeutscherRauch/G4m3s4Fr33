package com.example.g4m3s4fr33.data.bugs_and_glitches.remote

import com.example.g4m3s4fr33.data.model.gamingstuff.IWantToPlayUnrealTournament
import com.example.g4m3s4fr33.data.model.gamingstuff.SixteenTimesTheDetail
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

const val BASE_URL = "https://www.freetogame.com/api/"

private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
    level = HttpLoggingInterceptor.Level.BODY
}

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val httpClient = OkHttpClient.Builder()
    .addInterceptor(logger)
    .connectTimeout(30, TimeUnit.SECONDS)
    .readTimeout(30, TimeUnit.SECONDS)
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .client(httpClient)
    .build()


interface FreeTwoPlayMMOApiService {

    @GET("games")
    suspend fun getGameList(): List<IWantToPlayUnrealTournament>

    @GET("game")
    suspend fun getGameDetail(@Query("id") gameId: Int): SixteenTimesTheDetail

}

object FreeTwoPlayMMOApi {
    val retrofitService: FreeTwoPlayMMOApiService by lazy { retrofit.create(FreeTwoPlayMMOApiService::class.java) }
}