package com.lesa.features.films.ui_logic

import com.lesa.data.models.Film
import com.lesa.features.films.ui_logic.models.FilmUI

internal fun Film.toFilmUI(): FilmUI {
    return FilmUI(
        title = title,
        episodeId = episodeId,
        director = director,
        producer = producer,
        releaseYear = releaseYear.toString(),
        characters = characters,
        planets = planets

    )
}
