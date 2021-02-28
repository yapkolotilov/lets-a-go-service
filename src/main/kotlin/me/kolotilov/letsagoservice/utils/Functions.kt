package me.kolotilov.letsagoservice.utils

import java.util.*


fun <T : Any> Optional<T>.toNullable(): T? {
    return if (this.isPresent) get() else null
}

@Suppress("UNCHECKED_CAST")
fun <T> Any.castTo(): T {
    return this as T
}