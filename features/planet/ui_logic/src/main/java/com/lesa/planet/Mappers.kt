package com.lesa.planet

import com.lesa.data.RequestResult
import com.lesa.data.models.Planet
import com.lesa.planet.models.PlanetUI

internal fun Planet.toPlanetUI(): PlanetUI {
    return PlanetUI(
        name = name,
        diameter = diameter,
        gravity = gravity,
        population = population,
        climate = climate,
        terrain = terrain,
    )
}

internal fun RequestResult<PlanetUI>.toState(): State {
    return when (this) {
        is RequestResult.Error -> State.Error(planet = data, error = error)
        is RequestResult.InProgress -> State.Loading(data)
        is RequestResult.Success -> State.Success(data)
    }
}
