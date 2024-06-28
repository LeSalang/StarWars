package com.lesa.features.films.ui_logic

import com.lesa.features.films.ui_logic.models.FilmUI

sealed class State(public open val films: List<FilmUI>?) {

    data object None : State(films = null)

    class Loading(films: List<FilmUI>? = null) : State(films)

    class Error(films: List<FilmUI>? = null) : State(films)

    class Success(override val films: List<FilmUI>) : State(films)
}
