package com.lesa.data

import com.lesa.data.RequestResult.Error
import com.lesa.data.RequestResult.InProgress
import com.lesa.data.RequestResult.Success

interface MergeStrategy<E> {
    fun merge(
        cache: E,
        server: E
    ): E
}

internal class RequestResponseMergeStrategy<T : Any> : MergeStrategy<RequestResult<T>> {
    @Suppress("CyclomaticComplexMethod")
    override fun merge(
        cache: RequestResult<T>,
        server: RequestResult<T>
    ): RequestResult<T> {
        return when {
            cache is Error && server is InProgress -> merge(cache, server)
            cache is Error && server is Success -> merge(cache, server)
            cache is Error && server is Error -> merge(cache, server)
            cache is InProgress && server is Error -> merge(cache, server)
            cache is InProgress && server is InProgress -> merge(cache, server)
            cache is InProgress && server is Success -> merge(cache, server)
            cache is Success && server is Error -> merge(cache, server)
            cache is Success && server is InProgress -> merge(cache, server)
            cache is Success && server is Success -> merge(cache, server)
            else -> error("Unimplemented branch cache=$cache & server=$server")
        }
    }

    private fun merge(
        cache: InProgress<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return when {
            server.data != null -> InProgress(server.data)
            else -> InProgress(cache.data)
        }
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return InProgress(cache.data)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: InProgress<T>,
        server: Success<T>
    ): RequestResult<T> {
        return InProgress(server.data)
    }

    private fun merge(
        cache: Success<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(data = cache.data, error = server.error)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Success<T>,
        server: Success<T>
    ): RequestResult<T> {
        return Success(data = server.data)
    }

    private fun merge(
        cache: InProgress<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(data = server.data ?: cache.data, error = server.error)
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Error<T>,
        server: InProgress<T>
    ): RequestResult<T> {
        return server
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Error<T>,
        server: Success<T>
    ): RequestResult<T> {
        return server
    }

    @Suppress("UNUSED_PARAMETER")
    private fun merge(
        cache: Error<T>,
        server: Error<T>
    ): RequestResult<T> {
        return Error(data = null, error = server.error)
    }
}
