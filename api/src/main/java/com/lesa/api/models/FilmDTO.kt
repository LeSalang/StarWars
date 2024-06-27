package com.lesa.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmDTO(
    @SerialName("title") val title: String, // The title of this film
    @SerialName("episode_id") val episodeId: Int, // The episode number of this film
    @SerialName("director") val director: String, // The name of the director of this film
    @SerialName("producer") val producer: String, // The name(s) of the producer(s) of this film, comma separated
    @SerialName("release_date") val releaseDate: String, // The ISO 8601 date format of film release at original creator country
    @SerialName("characters") val characters: List<String>, // An array of people resource URLs that are in this film
    @SerialName("planets") val planets: List<String>, // An array of planet resource URLs that are in this film
)
