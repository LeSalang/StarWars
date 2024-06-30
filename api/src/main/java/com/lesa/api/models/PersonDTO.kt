package com.lesa.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param name The name of this person.
 * @param birthYear The birth year of the person, using the in-universe standard of BBY or ABY -
 *        Before the Battle of Yavin or After the Battle of Yavin.
 *        The Battle of Yavin is a battle that occurs at the end of Star Wars episode IV: A New Hope.
 * @param gender The gender of this person. Either "Male", "Female" or "unknown",
 *        "n/a" if the person does not have a gender.
 * @param homeworld The URL of a planet resource, a planet that this person was born on or inhabits.
 */
@Serializable
data class PersonDTO(
    @SerialName("name") val name: String,
    @SerialName("birth_year") val birthYear: String,
    @SerialName("gender") val gender: String,
    @SerialName("homeworld") val homeworld: String,
    @SerialName("url") val url: String,
)
