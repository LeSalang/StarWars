package com.lesa.features.planet.ui_logic

import com.lesa.ui_logic.models.PlanetUI

sealed class State(open val planet: PlanetUI?) {

    data object None : State(planet = null)

    class Loading(planet: PlanetUI? = null) : State(planet)

    class Error(planet: PlanetUI? = null, val error: Throwable?) : State(planet)

    class Success(override val planet: PlanetUI) : State(planet)
}
