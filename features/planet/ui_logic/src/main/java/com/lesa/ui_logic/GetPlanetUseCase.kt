package com.lesa.ui_logic

import com.lesa.data.PlanetRepository
import com.lesa.data.RequestResult
import com.lesa.data.map
import com.lesa.ui_logic.models.PlanetUI
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class GetPlanetUseCase @Inject constructor(
    private val repository: PlanetRepository
) {
    operator fun invoke(id: Int): Flow<RequestResult<PlanetUI>> {
        return repository.getPlanet(id = id)
            .map { requestResult ->
                requestResult.map { planet ->
                    planet.toPlanetUI()
                }
            }
    }
}
