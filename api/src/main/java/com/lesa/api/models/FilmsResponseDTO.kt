package com.lesa.api.models

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class FilmsResponseDTO(
    @SerialName("count") val count: Int,
    @SerialName("next") val next: String?,
    @SerialName("previous") val previous: String?,
    @SerialName("results") val results: List<FilmDTO>
)
