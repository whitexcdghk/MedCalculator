package com.medcalculator.wear.ui

/** Formats a value without a trailing ".0" for whole numbers, one decimal otherwise. */
fun formatValue(value: Double): String {
    val rounded = Math.round(value * 100) / 100.0
    return if (rounded == rounded.toLong().toDouble()) {
        rounded.toLong().toString()
    } else {
        rounded.toString()
    }
}
