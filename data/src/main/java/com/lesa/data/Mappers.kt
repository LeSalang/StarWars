package com.lesa.data

import com.lesa.api.models.FilmDTO
import com.lesa.api.models.PersonDTO
import com.lesa.api.models.PlanetDTO
import com.lesa.data.models.Film
import com.lesa.data.models.Person
import com.lesa.data.models.Planet
import com.lesa.database.models.FilmDBO
import com.lesa.database.models.PersonDBO
import com.lesa.database.models.PlanetDBO
import java.time.LocalDate
import java.time.format.DateTimeFormatter

internal fun FilmDTO.toFilm(): Film {
    return Film(
        title = title,
        episodeId = episodeId,
        director = director,
        producer = producer,
        releaseYear = parseReleaseDate(releaseDate)?.year,
        characterIDs = characters.mapNotNull { parseID(it) },
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
        characterIDs = characterIDs,
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
        characterIDs = characterIDs,
        planets = planets
    )
}

internal fun PersonDTO.toPerson(): Person {
    return Person(
        name = name,
        birthYear = birthYear,
        gender = gender,
        homeworldID = parseID(homeworld),
        personID = parseID(url)
    )
}

internal fun PersonDBO.toPerson(): Person {
    return Person(
        name = name,
        birthYear = birthYear,
        gender = gender,
        homeworldID = homeworldID,
        personID = personID
    )
}

internal fun Person.toPersonDBO(): PersonDBO {
    return PersonDBO(
        name = name,
        birthYear = birthYear,
        gender = gender,
        homeworldID = homeworldID,
        personID = personID
    )
}

internal fun PlanetDTO.toPlanet(): Planet {
    return Planet(
        name = name,
        diameter = diameter,
        gravity = gravity,
        population = population,
        climate = climate,
        terrain = terrain,
        id = parseID(url)
    )
}

internal fun PlanetDBO.toPlanet(): Planet {
    return Planet(
        name = name,
        diameter = diameter,
        gravity = gravity,
        population = population,
        climate = climate,
        terrain = terrain,
        id = id
    )
}

internal fun Planet.toPlanetDBO(): PlanetDBO {
    return PlanetDBO(
        name = name,
        diameter = diameter,
        gravity = gravity,
        population = population,
        climate = climate,
        terrain = terrain,
        id = id
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

private fun parseID(url: String): Int? {
    return url.dropLast(1).split('/').lastOrNull()?.toIntOrNull()
}
