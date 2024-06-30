package com.lesa.data

import com.lesa.api.SWApi
import com.lesa.api.models.FilmsResponseDTO
import com.lesa.api.models.PersonDTO
import com.lesa.data.models.Film
import com.lesa.data.models.Person
import com.lesa.database.SWDatabase
import com.lesa.database.models.FilmDBO
import com.lesa.database.models.PersonDBO
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.coroutineScope
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

    fun getPersons(idList: List<Int>): Flow<RequestResult<List<Person>>>
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

    override fun getPersons(idList: List<Int>): Flow<RequestResult<List<Person>>> {
        val personsFromDatabase: Flow<RequestResult<List<Person>>> = getPersonsFromDatabase(idList)
        val personsFromNetwork: Flow<RequestResult<List<Person>>> = getPersonsFromNetwork(idList)
        val mergeStrategy: MergeStrategy<RequestResult<List<Person>>> = RequestResponseMergeStrategy()

        return personsFromDatabase.combine(flow = personsFromNetwork, mergeStrategy::merge)
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
        val dbResponse: Flow<RequestResult<List<FilmDBO>>> = database.filmsDAO::getAll
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
        return database.filmsDAO
            .insertAll(
                films.map {
                    it.toFilmDBO()
                }
            )
    }

    private fun getPersonsFromNetwork(idList: List<Int>): Flow<RequestResult<List<Person>>> {
        val apiResponse = flow {
            emit(getPersonDTOsFromNetwork(idList))
        }.onEach { result ->
            if (result.isSuccess) {
                savePersonsToDatabase(
                    result.getOrThrow()
                        .map { personDTO ->
                            personDTO.toPerson()
                        }
                )
            }
        }.map { result ->
            result.toRequestResult()
        }

        val start = flowOf<RequestResult<List<PersonDTO>>>(RequestResult.InProgress())
        return merge(start, apiResponse)
            .map { result: RequestResult<List<PersonDTO>> ->
                result.map { personDTOList ->
                    personDTOList.map { personDTO ->
                        personDTO.toPerson()
                    }
                }
            }
    }

    private fun getPersonsFromDatabase(idList: List<Int>): Flow<RequestResult<List<Person>>> {
        val dbResponse: Flow<RequestResult<List<PersonDBO>>> = flow {
            emit(database.personsDAO.getAll(listID = idList))
        }
            .map<List<PersonDBO>, RequestResult<List<PersonDBO>>> { personDBOList ->
                RequestResult.Success(personDBOList)
            }.catch { throwable ->
                emit(RequestResult.Error(error = throwable))
            }

        val start = flowOf<RequestResult<List<PersonDBO>>>(RequestResult.InProgress())
        return merge(start, dbResponse)
            .map { requestResult ->
                requestResult.map { personDBOList ->
                    personDBOList.map { personDBO ->
                        personDBO.toPerson()
                    }
                }
            }
    }

    private suspend fun getPersonDTOsFromNetwork(idList: List<Int>): Result<List<PersonDTO>> {
        return coroutineScope {
            val deferredResults: List<Deferred<Result<PersonDTO>>> = idList.map {
                async {
                    api.getPerson(it)
                }
            }
            val list = deferredResults.awaitAll()
            val personDTOs = list.mapNotNull {
                it.getOrNull()
            }
            if (personDTOs.isEmpty() && list.isNotEmpty()) {
                val error = list.first().exceptionOrNull()
                Result.failure(error!!)
            } else {
                Result.success(personDTOs)
            }
        }
    }

    private suspend fun savePersonsToDatabase(persons: List<Person>) {
        return database.personsDAO
            .insertAll(
                persons.map {
                    it.toPersonDBO()
                }
            )
    }
}
