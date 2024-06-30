package com.lesa.api

import com.lesa.api.models.FilmsResponseDTO
import com.lesa.api.models.PersonDTO
import com.skydoves.retrofit.adapters.result.ResultCallAdapterFactory
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET
import retrofit2.http.Query

/**
* [API Documentation](https://swapi.dev/documentation)
*/
interface SWApi {

    @GET(GET_FILMS_REQUEST)
    suspend fun getFilms(): Result<FilmsResponseDTO>

    @GET(GET_PERSON_REQUEST)
    suspend fun getPerson(
        @Query("id") id: Int
    ): Result<PersonDTO>
}

fun createSWApi(
    baseUrl: String,
    okHttpClient: OkHttpClient? = null,
): SWApi {
    val json = Json { ignoreUnknownKeys = true }

    return retrofit(
        baseUrl = baseUrl,
        okHttpClient = okHttpClient,
        json = json
    ).create()
}

private fun retrofit(
    baseUrl: String,
    okHttpClient: OkHttpClient?,
    json: Json = Json
): Retrofit {
    val jsonConverter: Converter.Factory = json.asConverterFactory("application/json".toMediaType())

    val modifiedOkHttpClient = (okHttpClient?.newBuilder() ?: OkHttpClient.Builder())
        .addInterceptor(SWApiKeyInterceptor())
        .build()

    return Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(jsonConverter)
        .addCallAdapterFactory(ResultCallAdapterFactory.create())
        .client(modifiedOkHttpClient)
        .build()
}

const val GET_FILMS_REQUEST = "films"
const val GET_PERSON_REQUEST = "people"
