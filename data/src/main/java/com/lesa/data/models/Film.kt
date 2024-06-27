package com.lesa.data.models

import java.time.LocalDate

data class Film(
    val title: String,
    val episodeId: Int,
    val director: String,
    val producer: String,
    val releaseDate: LocalDate?,
    val characters: List<String>,
    val planets: List<String>,
)
