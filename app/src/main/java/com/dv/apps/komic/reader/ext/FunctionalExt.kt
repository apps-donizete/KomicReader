package com.dv.apps.komic.reader.ext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

inline infix fun <A, B> ((A) -> Unit).dispatchFor(
    crossinline other: (B) -> A
): (B) -> Unit = {
    invoke(other(it))
}

inline fun <T, R> Flow<List<T>>.mapItems(
    crossinline block: (T) -> R
) = map {
    it.map(block)
}