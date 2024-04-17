package com.example.g4m3s4fr33.data.bugs_and_glitches.remote

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory


const val BASE_URL = "https://www.freetogame.com/api/"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .baseUrl(BASE_URL)
    .build()

// TODO
interface FreeTwoPlayMMOApiService {

}

object FreeTwoPlayMMOApi {
    val retrofitService: FreeTwoPlayMMOApiService by lazy { retrofit.create(FreeTwoPlayMMOApiService::class.java) }
}