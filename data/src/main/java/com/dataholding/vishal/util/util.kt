package com.dataholding.vishal.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

fun Double?.FormattedAmount(): Double {

    val otherSymbols = DecimalFormatSymbols(Locale.US)
    val decimalFormat = DecimalFormat("#,###,###.##", otherSymbols)
    return decimalFormat.format(this).toDouble()

}
