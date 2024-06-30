package com.lesa.starwars

import android.content.Context
import com.lesa.api.SWApi
import com.lesa.api.createSWApi
import com.lesa.data.FilmRepository
import com.lesa.data.FilmRepositoryImpl
import com.lesa.data.PersonRepository
import com.lesa.data.PersonRepositoryImpl
import com.lesa.data.PlanetRepository
import com.lesa.data.PlanetRepositoryImpl
import com.lesa.database.SWDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideSWApi(okHttpClient: OkHttpClient?): SWApi {
        return createSWApi(
            baseUrl = BuildConfig.SW_API_BASE_URL,
            okHttpClient = okHttpClient
        )
    }

    @Provides
    @Singleton
    fun provideHttpClient(): OkHttpClient {
        val logging =
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    @Provides
    @Singleton
    fun provideSWDatabase(
        @ApplicationContext context: Context
    ): SWDatabase {
        return SWDatabase(context)
    }

    @Provides
    @Singleton
    fun providePersonRepository(swApi: SWApi, swDatabase: SWDatabase): PersonRepository {
        return PersonRepositoryImpl(swApi, swDatabase)
    }

    @Provides
    @Singleton
    fun provideFilmRepository(swApi: SWApi, swDatabase: SWDatabase): FilmRepository {
        return FilmRepositoryImpl(swApi, swDatabase)
    }

    @Provides
    @Singleton
    fun providePlanetRepository(swApi: SWApi, swDatabase: SWDatabase): PlanetRepository {
        return PlanetRepositoryImpl(swApi, swDatabase)
    }
}
