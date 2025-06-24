package com.dataholding.vishal.core.error

import com.dataholding.vishal.core.util.ErrorMsg
import java.io.IOException

sealed class Failure {
    data class NetworkError(val exception: IOException) : Failure()
    data class ServerError(val code: Int, val message: String) : Failure()
    data class UnknownError(val throwable: Throwable) : Failure()
}


fun Failure.getError(): Pair<Int?, String?>? {
    return when (this) {
        is Failure.NetworkError -> Pair(ErrorMsg.ERR_UNKNOWN_NETWORK.msg, exception.message)
        is Failure.ServerError -> Pair(0, message)
        is Failure.UnknownError -> Pair(ErrorMsg.ERR_UNKNOWN.msg, throwable.message)
    }
}