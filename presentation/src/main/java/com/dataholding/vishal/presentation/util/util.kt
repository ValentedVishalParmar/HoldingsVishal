package com.dataholding.vishal.presentation.util

import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale


fun Double?.FormattedAmount(): String {

    val otherSymbols = DecimalFormatSymbols(Locale.US)
    val decimalFormat = DecimalFormat("#,###,###.##", otherSymbols)
    return decimalFormat.format(this).toString()

}