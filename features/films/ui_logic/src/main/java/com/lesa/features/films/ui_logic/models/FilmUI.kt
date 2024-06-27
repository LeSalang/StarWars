package com.lesa.features.films.ui_logic.models

data class FilmUI(
    val title: String,
    val episodeId: Int,
    val director: String,
    val producer: String,
    val releaseYear: String,
    val characters: List<String>,
    val planets: List<String>,
)
