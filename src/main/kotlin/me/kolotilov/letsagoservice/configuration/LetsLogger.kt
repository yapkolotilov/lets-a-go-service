package me.kolotilov.letsagoservice.configuration

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

interface LetsLogger {

    companion object {

        operator fun invoke(tag: String): LetsLogger {
            return LetsLoggerImpl(tag)
        }
    }

    fun info(message: Any)

    fun warn(message: Any)

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
        return "${dateFormat.format(Date())}: $level: $tag: $message"
    }
}