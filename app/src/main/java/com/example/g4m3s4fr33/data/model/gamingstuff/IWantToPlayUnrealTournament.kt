package com.example.g4m3s4fr33.data.model.gamingstuff

data class IWantToPlayUnrealTournament(
    val id: Int,
    val title: String,
    val thumbnail: String,
   // @Json(name = "short_description")
    val description: String,
  //  @Json(name = "game_url")
    val url: String,
    val genre: String,
    val platform: String,
    val publisher: String,
    val developer: String,
   // @Json(name = "release_date")
    val release: String
)
