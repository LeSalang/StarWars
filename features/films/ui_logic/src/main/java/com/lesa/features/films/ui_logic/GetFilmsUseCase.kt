package com.lesa.features.films.ui_logic

import android.util.Log
import com.lesa.data.FilmRepository
import com.lesa.data.RequestResult
import com.lesa.data.map
import com.lesa.features.films.ui_logic.models.FilmUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetFilmsUseCase @Inject constructor(
    private val repository: FilmRepository
) {
    operator fun invoke(): Flow<RequestResult<List<FilmUI>>> {
        return repository.getAllFilms()
            .map { requestResult ->
                requestResult.map { filmList ->
                    filmList.map { film ->
                        Log.d("GetFilmsUseCase", "invoke: $film")
                        film.toFilmUI()
                    }
                }
            }
    }
}
