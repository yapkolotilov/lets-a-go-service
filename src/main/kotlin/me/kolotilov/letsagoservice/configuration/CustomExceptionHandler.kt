package me.kolotilov.letsagoservice.configuration

import me.kolotilov.letsagoservice.presentation.output.ErrorDto
import me.kolotilov.letsagoservice.presentation.output.toErrorDto
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.client.HttpStatusCodeException
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Кастомный обработчик ошибок.
 */
@ControllerAdvice
class CustomExceptionHandler : ResponseEntityExceptionHandler() {

    private val log = LetsLogger("EXCEPTION_HANDLER")


    /**
     * Преобразовываем [ServiceException] в [ErrorDto для отображения на фронте.
     */
    @ExceptionHandler(Exception::class)
    fun handleConflict(e: Exception, request: WebRequest): ResponseEntity<*> {
        if (e is ServiceException) {
            log.warn(e.toString())
            return handleExceptionInternal(e, e.toErrorDto(), HttpHeaders(), e.status, request)
        }
        val status = if (e is HttpStatusCodeException) e.statusCode else HttpStatus.BAD_REQUEST
        val body = ErrorDto(
            code = ErrorCode.OTHER,
            status = status.value(),
            message = e.message ?: "",
            stackTrace = e.stackTraceToString()
        )
        log.error(e.toString())
        return ResponseEntity(body, status)
    }
}