package com.lesa.films.models

data class FilmUI(
    val title: String,
    val episodeId: Int,
    val director: String,
    val producer: String,
    val releaseYear: String,
    val characters: List<Int>,
    val planets: List<String>,
)
