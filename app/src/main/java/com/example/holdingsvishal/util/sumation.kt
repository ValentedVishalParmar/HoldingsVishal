package com.example.holdingsvishal.util

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
fun main() {

   for (i in 1..250) {
        val num = i
        if (num >= 10) {
            val strValue = num.toString()
            if (strValue.length == 3) {
                if (strValue.take(1).toInt() + strValue.substring(1, 2).toInt() + strValue.takeLast(
                        1
                    ).toInt() == 7
                ) {
                    println(7)
                } else {
                    val sum = strValue.take(1).toInt() + strValue.substring(1, 2)
                        .toInt() + strValue.takeLast(1).toInt()
                    if (sum.toString().length >= 2) {
                        if (sum.toString().take(1).toInt() + sum.toString().takeLast(1)
                                .toInt() == 7
                        ) {
                            println(7)
                        } else {
                            println(strValue)
                        }
                    } else if(sum  == 7 ){
                        println(7)
                    } else {
                        println(strValue)
                    }

                }

            } else {
                if (strValue.take(1).toInt() + strValue.takeLast(1).toInt() == 7) {
                    println(7)
                } else {
                    val sum = strValue.take(1).toInt() + strValue.takeLast(1).toInt()
                    if (sum.toString().length >= 2) {
                        if (sum.toString().take(1).toInt() + sum.toString().takeLast(1)
                                .toInt() == 7
                        ) {
                            println(7)
                        } else {
                            println(strValue)
                        }
                    } else if (sum == 7) {
                        println(7)
                    } else {
                        println(strValue)
                    }
                }

            }

        }

    }

}




