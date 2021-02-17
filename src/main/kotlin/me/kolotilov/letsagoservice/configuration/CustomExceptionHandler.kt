package me.kolotilov.letsagoservice.configuration

import mu.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    private val logger = KotlinLogging.logger("ERRORS")

    @ExceptionHandler(Exception::class)
    fun handleConflict(e: Exception, request: WebRequest): ResponseEntity<*> {
        val status = if (e is HttpStatusCodeException) e.statusCode else HttpStatus.BAD_REQUEST
        val body = linkedMapOf(
            "error" to e.message
        )
        logger.error(e)
        return handleExceptionInternal(e, body, HttpHeaders(), status, request)
    }
}