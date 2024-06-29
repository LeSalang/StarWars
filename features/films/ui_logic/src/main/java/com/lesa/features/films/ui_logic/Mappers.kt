package com.lesa.features.films.ui_logic

import com.lesa.data.RequestResult
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

internal fun RequestResult<List<FilmUI>>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error(films = data, error = error)
        is RequestResult.InProgress -> State.Loading(data)
        is RequestResult.Success -> State.Success(data)
    }
}
