package com.example.g4m3s4fr33.data.model.gamingstuff

import com.squareup.moshi.Json

data class SixteenTimesTheDetail(
    val id: Int,
    val title: String,
    val thumbnail: String,
    val status: String,
    @Json(name = "short_description")
    val descriptionShort: String,
    @Json(name = "description")
    val descriptionLong: String,
    @Json(name = "game_url")
    val url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
    @Json(name = "release_date")
    val release: String,
    @Json(name = "minimum_system_requirements")
    val minimumSystemRequirements: SystemRequirements? = null
)

data class SystemRequirements(
    val os: String,
    val processor: String,
    val memory: String,
    val graphics: String,
    val storage: String
)

