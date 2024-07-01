package com.lesa.data

import com.lesa.api.SWApi
import com.lesa.api.models.PlanetDTO
import com.lesa.data.models.Planet
import com.lesa.database.SWDatabase
import com.lesa.database.models.PlanetDBO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface PlanetRepository {
    fun getPlanet(id: Int): Flow<RequestResult<Planet>>
}

class PlanetRepositoryImpl @Inject constructor(
    private val api: SWApi,
    private val database: SWDatabase
) : PlanetRepository {

    override fun getPlanet(id: Int): Flow<RequestResult<Planet>> {
        val planetFromDatabase: Flow<RequestResult<Planet>> = getPlanetFromDatabase(id)
        val planetFromNetwork: Flow<RequestResult<Planet>> = getPlanetFromNetwork(id)
        val mergeStrategy: MergeStrategy<RequestResult<Planet>> = RequestResponseMergeStrategy()

        return planetFromDatabase.combine(flow = planetFromNetwork, mergeStrategy::merge)
    }

    private fun getPlanetFromNetwork(id: Int): Flow<RequestResult<Planet>> {
        val apiResponse = flow {
            emit(api.getPlanet(id = id))
        }
            .onEach { result ->
                if (result.isSuccess) {
                    savePlanetToDatabase(result.getOrThrow().toPlanet())
                }
            }
            .map { result ->
                result.toRequestResult()
            }

        val start = flowOf<RequestResult<PlanetDTO>>(RequestResult.InProgress())
        return merge(start, apiResponse)
            .map { requestResult ->
                requestResult.map { planetDTO ->
                    planetDTO.toPlanet()
                }
            }
    }

    private fun getPlanetFromDatabase(id: Int): Flow<RequestResult<Planet>> {
        val dbResponse: Flow<RequestResult<PlanetDBO>> = flow {
            emit(database.planetDAO.getPlanet(id = id))
        }
            .map<PlanetDBO, RequestResult<PlanetDBO>> { planetDBO ->
                RequestResult.Success(planetDBO)
            }
            .catch { throwable ->
                emit(RequestResult.Error(error = throwable))
            }

        val start = flowOf<RequestResult<PlanetDBO>>(RequestResult.InProgress())
        return merge(start, dbResponse)
            .map { requestResult ->
                requestResult.map { planetDBO ->
                    planetDBO.toPlanet()
                }
            }
    }

    private suspend fun savePlanetToDatabase(planet: Planet) {
        return database.planetDAO.insertPlanet(planet = planet.toPlanetDBO())
    }
}
