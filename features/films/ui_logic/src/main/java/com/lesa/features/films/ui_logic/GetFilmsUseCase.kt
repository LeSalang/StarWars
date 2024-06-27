package com.lesa.features.films.ui_logic

import com.lesa.data.SWRepository
import com.lesa.features.films.ui_logic.models.FilmUI
import javax.inject.Inject

internal class GetFilmsUseCase @Inject constructor(
    private val repository: SWRepository
) {
    suspend fun invoke(location: String): List<FilmUI> {
        return repository.getAllFilms()
            .map {
                it.toFilmUI()
            }
    }
}
