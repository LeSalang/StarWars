package com.lesa.data

import com.lesa.api.SWApi
import com.lesa.data.models.Film
import javax.inject.Inject

interface SWRepository {
    suspend fun getAllFilms(): List<Film>
}

class SWRepositoryImpl @Inject constructor(
    private val api: SWApi
) : SWRepository {
    override suspend fun getAllFilms(): List<Film> {
        TODO("Not yet implemented")
    }
}
