package com.dataholding.vishal.data.remote.handler

import com.dataholding.vishal.core.error.Failure
import com.dataholding.vishal.core.functional.Either
import okio.IOException
import retrofit2.HttpException

fun Throwable.toEither(): Either<Failure, Nothing> {
    return when (this) {
        is IOException -> Either.Left(Failure.NetworkError(this))
        is HttpException -> Either.Left(Failure.ServerError(code(), message()))
        else -> Either.Left(Failure.UnknownError(this))
    }
}