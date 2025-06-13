package com.dataholding.vishal.core.mapper

fun interface ResultMapper<T,R> {
    fun map(map:T): R
}
