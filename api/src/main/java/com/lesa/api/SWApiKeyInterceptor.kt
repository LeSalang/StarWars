package com.lesa.api

import okhttp3.Interceptor
import okhttp3.Response

internal class SWApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .url(
                chain.request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", apiKey)
                    .build()
            )
            .build()
        return chain.proceed(request)
    }
}
