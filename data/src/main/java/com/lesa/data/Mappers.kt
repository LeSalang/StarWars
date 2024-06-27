package com.lesa.data

import com.lesa.api.models.FilmDTO
import com.lesa.data.models.Film
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun FilmDTO.toFilm(): Film {
    return Film(
        title = title,
        episodeId = episodeId,
        director = director,
        producer = producer,
        releaseDate = parseReleaseDate(releaseDate),
        characters = characters,
        planets = planets
    )
}

fun parseReleaseDate(dateInISO8601: String): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE
        LocalDate.parse(dateInISO8601, formatter)
    } catch (e: Exception) {
        null
    }
}
