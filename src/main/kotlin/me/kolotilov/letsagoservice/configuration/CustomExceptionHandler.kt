package me.kolotilov.letsagoservice.configuration

import me.kolotilov.letsagoservice.presentation.output.ErrorDto
import me.kolotilov.letsagoservice.presentation.output.toErrorDto
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

    private val log = KotlinLogging.logger("ERRORS")

    @ExceptionHandler(ServiceException::class)
    fun handleServiceConflict(e: ServiceException, request: WebRequest): ResponseEntity<*> {
        log.error(e.toString())
        e.printStackTrace()
        return handleExceptionInternal(e, e.toErrorDto(), HttpHeaders(), e.status, request)
    }

    @ExceptionHandler(Exception::class)
    fun handleConflict(e: Exception, request: WebRequest): ResponseEntity<*> {
        val status = if (e is HttpStatusCodeException) e.statusCode else HttpStatus.BAD_REQUEST
        val body = ErrorDto(
            code = ErrorCode.OTHER,
            status = status.value(),
            message = e.message ?: "",
            stackTrace = e.stackTraceToString()
        )
        log.error(e.toString())
        e.printStackTrace()
        return handleExceptionInternal(e, body, HttpHeaders(), status, request)
    }
}