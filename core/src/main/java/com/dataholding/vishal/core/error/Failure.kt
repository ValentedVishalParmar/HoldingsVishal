package com.dataholding.vishal.core.error

import com.dataholding.vishal.core.util.ErrorMessage.ERR_UNKNOWN
import com.dataholding.vishal.core.util.ErrorMessage.ERR_UNKNOWN_NETWORK
import java.io.IOException

sealed class Failure {
    data class NetworkError(val exception: IOException) : Failure()
    data class ServerError(val code: Int, val message: String) : Failure()
    data class UnknownError(val throwable: Throwable) : Failure()
}

fun Failure.getError(): String {
    return when (this) {
        is Failure.NetworkError -> ERR_UNKNOWN_NETWORK.plus(exception.message)
        is Failure.ServerError -> message
        is Failure.UnknownError -> ERR_UNKNOWN.plus(throwable.message)
    }
}