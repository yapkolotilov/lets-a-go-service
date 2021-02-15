package me.kolotilov.letsagoservice.domain.models

/**
 * Пользователь.
 *
 * @param username Имя пользователя (e-mail).
 * @param password Пароль.
 * @param confirmationUrl URL подтверждения.
 * @param enabled Подтверждён ли аккаунт.
 */
data class User(
    val username: String,
    val password: String,
    val confirmationUrl: String,
    val enabled: Boolean
)
