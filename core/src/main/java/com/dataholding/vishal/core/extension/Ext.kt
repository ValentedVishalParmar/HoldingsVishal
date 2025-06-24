package com.dataholding.vishal.core.extension

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * Returns the value of the Boolean or the provided default if null.
 *
 * @param default The default value to return if the Boolean is null. Default is false.
 * @return The Boolean value or the default.
 *
 * Usage:
 * val isActive = someNullableBoolean.orDefault()
 */
fun Boolean?.orDefault(default: Boolean = false): Boolean = this ?: default

/**
 * Maps a nullable list to another list using the provided transform, or returns a default list if null.
 *
 * @param defaultListValue The list to return if the original list is null. Default is emptyList().
 * @param transform The mapping function to apply to each element.
 * @return The mapped list or the default list.
 *
 * Usage:
 * val result = nullableList.mapOrDefault { it.toString() }
 */
fun <T, R : Any> List<T>?.mapOrDefault(
    defaultListValue: List<R> = emptyList(),
    transform: (T) -> R
): List<R> {
    return this?.filterNotNull()?.map(transform) ?: defaultListValue
}

/**
 * Creates a [StateFlow] from a [Flow] that is active while subscribed, with an initial value and a pre-fetch action.
 *
 * @param scope The [CoroutineScope] in which to launch the state flow.
 * @param initial The initial value of the state flow.
 * @param preFetch A lambda to execute before collecting the flow.
 * @return The resulting [StateFlow].
 *
 * Usage:
 * val state = flow.stateInWhileActive(scope, initialValue) { preFetchAction() }
 */
fun <T> Flow<T>.stateInWhileActive(
    scope: CoroutineScope,
    initial: T,
    preFetch: () -> Unit
): StateFlow<T> {
    return onStart {
        preFetch()
    }.stateIn(
        scope = scope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = initial
    )
}

/**
 * Formats a nullable [Double] as a currency string with grouping separators.
 *
 * @receiver The [Double] value to format.
 * @return The formatted string, or null if the receiver is null.
 *
 * Usage:
 * val formatted = amount.FormattedAmount()
 */
fun Double?.FormattedAmount(): String? {
    val otherSymbols = DecimalFormatSymbols(Locale.US)
    val decimalFormat = DecimalFormat("#,###,###.##", otherSymbols)
    return decimalFormat.format(this).toString()
}