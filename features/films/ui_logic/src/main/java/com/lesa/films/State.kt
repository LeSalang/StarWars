package com.lesa.films

import com.lesa.films.models.FilmUI

sealed class State(open val films: List<FilmUI>?) {

    data object None : State(films = null)

    class Loading(films: List<FilmUI>? = null) : State(films)

    class Error(films: List<FilmUI>? = null, val error: Throwable?) : State(films)

    class Success(override val films: List<FilmUI>) : State(films)
}
