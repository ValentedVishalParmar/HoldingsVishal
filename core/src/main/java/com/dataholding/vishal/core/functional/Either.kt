package com.dataholding.vishal.core.functional

// this class represent more expressive way of response handling.
// left == error, right == success
sealed class Either <out L, out R> {
    data class Left<out T>(val value: T) : Either<T, Nothing>()
    data class Right<out T>(val value: T) : Either<Nothing, T>()
}
    inline fun<L,R,T> Either<L,R>.fold(
        left: (L)->T,
        right:(R)->T,) = when(this) {
        is Either.Left -> left(value)
        is Either.Right -> right(value)
    }
