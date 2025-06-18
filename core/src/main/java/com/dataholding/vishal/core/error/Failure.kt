package com.dataholding.vishal.core.error

import java.io.IOException

sealed class Failure {
    data class NetworkError(val exception: IOException): Failure()
    data class ServerError(val code: Int, val message: String): Failure()
    data class UnknownError(val throwable: Throwable): Failure()
}

fun Failure.getError() : String {
   return when(this) {
      is  Failure.NetworkError -> "Network Error occurred: ${exception.message}"
       is Failure.ServerError ->  message
       is Failure.UnknownError -> "An Unknown Error occurred: ${throwable.message} "
    }
}