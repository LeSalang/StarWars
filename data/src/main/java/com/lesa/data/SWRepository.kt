package com.lesa.data

import com.lesa.api.SWApi
import com.lesa.api.models.FilmsResponseDTO
import com.lesa.data.models.Film
import com.lesa.database.SWDatabase
import com.lesa.database.models.FilmDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface SWRepository {
    fun getAllFilms(): Flow<RequestResult<List<Film>>>
}

class SWRepositoryImpl @Inject constructor(
    private val api: SWApi,
    private val database: SWDatabase
) : SWRepository {

    override fun getAllFilms(): Flow<RequestResult<List<Film>>> {
        val filmsFromDatabase: Flow<RequestResult<List<Film>>> = getFilmsFromDatabase()
        val filmsFromNetwork: Flow<RequestResult<List<Film>>> = getFilmsFromNetwork()
        val mergeStrategy: MergeStrategy<RequestResult<List<Film>>> = RequestResponseMergeStrategy()

        return filmsFromDatabase.combine(flow = filmsFromNetwork, mergeStrategy::merge)
    }

    private fun getFilmsFromNetwork(): Flow<RequestResult<List<Film>>> {
        val apiResponse = flow {
            emit(api.getFilms())
        }.onEach { result ->
            if (result.isSuccess) {
                saveFilmsToDatabase(
                    result.getOrThrow()
                        .results.map { filmDTO ->
                            filmDTO.toFilm()
                        }
                )
            }
        }.map { result ->
            result.toRequestResult()
        }

        val start = flowOf<RequestResult<FilmsResponseDTO>>(RequestResult.InProgress())
        return merge(start, apiResponse)
            .map { result: RequestResult<FilmsResponseDTO> ->
                result.map { response ->
                    response.results.map { filmDTO ->
                        filmDTO.toFilm()
                    }
                }
            }
    }

    private fun getFilmsFromDatabase(): Flow<RequestResult<List<Film>>> {
        val dbResponse = database.filmsDao::getAll
            .asFlow()
            .map<List<FilmDBO>, RequestResult<List<FilmDBO>>> { filmDBOList ->
                RequestResult.Success(filmDBOList)
            }.catch { throwable ->
                emit(RequestResult.Error(error = throwable))
            }

        val start = flowOf<RequestResult<List<FilmDBO>>>(RequestResult.InProgress())
        return merge(start, dbResponse)
            .map { requestResult ->
                requestResult.map { filmDBOList ->
                    filmDBOList.map { filmDBO ->
                        filmDBO.toFilm()
                    }
                }
            }
    }

    private suspend fun saveFilmsToDatabase(films: List<Film>) {
        return database.filmsDao
            .insertAll(
                films.map {
                    it.toFilmDBO()
                }
            )
    }
}
