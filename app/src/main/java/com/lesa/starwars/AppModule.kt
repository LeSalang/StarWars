package com.lesa.starwars

import android.content.Context
import com.lesa.api.SWApi
import com.lesa.api.createSWApi
import com.lesa.data.SWRepository
import com.lesa.data.SWRepositoryImpl
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
    fun provideSWRepository(swApi: SWApi): SWRepository {
        return SWRepositoryImpl(swApi)
    }
}
