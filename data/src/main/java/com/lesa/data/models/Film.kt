package com.lesa.data.models

data class Film(
    val title: String,
    val episodeId: Int,
    val director: String,
    val producer: String,
    val releaseYear: Int?,
    val characters: List<String>,
    val planets: List<String>,
)
