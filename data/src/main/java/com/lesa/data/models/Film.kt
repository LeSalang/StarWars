package com.lesa.data.models

data class Film(
    val title: String,
    val episodeId: Int,
    val director: String,
    val producer: String,
    val releaseYear: Int?,
    val characterIDs: List<Int>,
    val planets: List<String>,
)
