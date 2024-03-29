package me.kolotilov.letsagoservice.configuration

import org.springframework.http.HttpStatus

/**
 * Ошибка бизнес-логики.
 */
data class ServiceException(
    val code: ErrorCode,
    val status: HttpStatus = HttpStatus.BAD_REQUEST,
    override val message: String = "",
    val stackTrace: String = ""
) : Exception()

fun Exception.toServiceException(
    code: ErrorCode,
    status: HttpStatus,
    message: String = this.message ?: ""
): ServiceException {
    return ServiceException(code, status, message, stackTraceToString())
}

/**
 * Код ошибки.
 */
enum class ErrorCode {

    /**
     * Неизвестная ошибка.
     */
    OTHER,

    /**
     * Пользователь не существует.
     */
    USER_NOT_EXISTS,

    /**
     * Неправильный пароль.
     */
    INVALID_PASSWORD,

    /**
     * Неправильный email.
     */
    INVALID_USERNAME,

    /**
     * Пользователь уже зарегистрирован.
     */
    USER_ALREADY_EXITS,

    /**
     * Слишком короткий маршрут.
     */
    ENTRY_TOO_SHORT,

    /**
     * Слишком высокая скорость.
     */
    SPEED_TOO_FAST,

    /**
     * Слишком далеко от маршрута.
     */
    TOO_FAR_FROM_ROUTE,

    CONFIRM_EMAIL
}