package com.lesa.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

/**
 * @param title The title of this film
 * @param episodeId The episode number of this film
 * @param director The name of the director of this film
 * @param producer The name(s) of the producer(s) of this film, comma separated
 * @param releaseDate The ISO 8601 date format of film release at original creator country
 * @param characters An array of people resource URLs that are in this film
 * @param planets An array of planet resource URLs that are in this film
 */
@Serializable
data class FilmDTO(
    @SerialName("title") val title: String,
    @SerialName("episode_id") val episodeId: Int,
    @SerialName("director") val director: String,
    @SerialName("producer") val producer: String,
    @SerialName("release_date") val releaseDate: String,
    @SerialName("characters") val characters: List<String>,
    @SerialName("planets") val planets: List<String>,
)
