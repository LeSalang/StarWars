package com.lesa.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * A Planet resource is a large mass, planet or planetoid in the Star Wars Universe, at the time of 0 ABY.
 *
 * @param name The name of this planet.
 * @param diameter The diameter of this planet in kilometers.
 * @param gravity A number denoting the gravity of this planet,
 *        where "1" is normal or 1 standard G. "2" is twice or 2 standard Gs. "0.5" is half or 0.5 standard Gs.
 * @param population The average population of sentient beings inhabiting this planet.
 * @param climate The climate of this planet. Comma separated if diverse.
 * @param terrain The terrain of this planet. Comma separated if diverse.
 */
@Serializable
data class PlanetDTO(
    @SerialName("name") val name: String,
    @SerialName("diameter") val diameter: String,
    @SerialName("gravity") val gravity: String,
    @SerialName("population") val population: String,
    @SerialName("climate") val climate: String,
    @SerialName("terrain") val terrain: String,
    @SerialName("url") val url: String,
)
