package com.lesa.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponseDTO(
    @SerialName("results") val results: List<FilmDTO>
)
