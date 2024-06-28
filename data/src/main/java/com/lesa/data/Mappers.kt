package com.lesa.data

import com.lesa.api.models.FilmDTO
import com.lesa.data.models.Film
import com.lesa.database.models.FilmDBO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun FilmDTO.toFilm(): Film {
    return Film(
        title = title,
        episodeId = episodeId,
        director = director,
        producer = producer,
        releaseYear = parseReleaseDate(releaseDate)?.year,
        characters = characters,
        planets = planets
    )
}

internal fun FilmDBO.toFilm(): Film {
    return Film(
        title = title,
        episodeId = episodeId,
        director = director,
        producer = producer,
        releaseYear = releaseYear,
        characters = characters,
        planets = planets
    )
}

internal fun Film.toFilmDBO(): FilmDBO {
    return FilmDBO(
        title = title,
        episodeId = episodeId,
        director = director,
        producer = producer,
        releaseYear = releaseYear,
        characters = characters,
        planets = planets
    )
}

@Suppress(
    "TooGenericExceptionCaught",
    "SwallowedException",
)
private fun parseReleaseDate(dateInISO8601: String): LocalDate? {
    return try {
        val formatter = DateTimeFormatter.ISO_DATE
        LocalDate.parse(dateInISO8601, formatter)
    } catch (e: Exception) {
        null
    }
}
