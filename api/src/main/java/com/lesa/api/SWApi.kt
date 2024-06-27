package com.lesa.api

import com.lesa.api.models.FilmsResponseDTO
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import retrofit2.create
import retrofit2.http.GET

// [API Documentation](https://swapi.dev/documentation)
interface SWApi {

    @GET(GET_ALL_FILMS_REQUEST)
    suspend fun getAllFilms(): FilmsResponseDTO
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
        .client(modifiedOkHttpClient)
        .build()
}

const val GET_ALL_FILMS_REQUEST = "films"
