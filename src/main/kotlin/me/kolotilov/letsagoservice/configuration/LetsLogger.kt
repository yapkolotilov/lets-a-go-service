package me.kolotilov.letsagoservice.configuration

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

/**
 * Логгер для проекта.
 */
interface LetsLogger {

    companion object {

        operator fun invoke(tag: String): LetsLogger {
            return LetsLoggerImpl(tag)
        }
    }

    /**
     * Логгинг уровня INFO.
     *
     * @param message Сообщение.
     */
    fun info(message: Any)

    /**
     * Логгинг уровня WARN.
     *
     * @param message Сообщение.
     */
    fun warn(message: Any)

    /**
     * Логгинг уровня ERROR.
     *
     * @param message Сообщение.
     */
    fun error(message: Any)
}

private class LetsLoggerImpl(
    private val tag: String
) : LetsLogger {

    private val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd : hh:mm:ss")

    override fun info(message: Any) {
        println(message("INFO", message))
    }

    override fun warn(message: Any) {
        println(message("WARN", message))
    }

    override fun error(message: Any) {
        System.err.println(message("ERROR", message))
    }

    private fun message(level: String, message: Any): String {
        return "${dateFormat.format(Date())} : $level: [$tag]: $message"
    }
}