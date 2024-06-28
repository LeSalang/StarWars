package com.lesa.features.films.ui_logic

import com.lesa.data.RequestResult
import com.lesa.data.SWRepository
import com.lesa.data.map
import com.lesa.features.films.ui_logic.models.FilmUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetFilmsUseCase @Inject constructor(
    private val repository: SWRepository
) {
    operator fun invoke(): Flow<RequestResult<List<FilmUI>>> {
        return repository.getAllFilms()
            .map { requestResult ->
                requestResult.map { filmList ->
                    filmList.map { film ->
                        film.toFilmUI()
                    }
                }
            }
    }
}
