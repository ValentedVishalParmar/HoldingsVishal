package com.dataholding.vishal.data.remote.handler

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import com.dataholding.vishal.core.mapper.ResultMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Response

suspend fun <T, R> safeApiCall(
    ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    apiCall: suspend () -> Response<T>,
    mapper: ResultMapper<T, R>
): Either<Failure, R> {

    return withContext(ioDispatcher) {
        runCatching {
            val response = apiCall()
            if (response.isSuccessful) {
                response.body()?.let {
                    Either.Right(mapper.map(it))
                } ?: Either.Left(
                    Failure.ServerError(
                        code = response.code(),
                        message = response.message()
                    )
                )
            } else {

                Either.Left(Failure.ServerError(response.code(), response.message()))
            }
        }.getOrElse { it.toEither() }
    }
}