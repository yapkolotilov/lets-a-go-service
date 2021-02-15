package me.kolotilov.letsagoservice.utils

import java.util.*

/**
 * Печатает текст в консоль с тегом
 *
 * @param tag Тег.
 * @param message Текст.
 */
fun log(tag: String, message: Any?) {
    println("$tag $message")
}

fun <T : Any> Optional<T>.toNullable(): T? {
    return if (this.isPresent) get() else null
}