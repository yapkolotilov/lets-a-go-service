package me.kolotilov.letsagoservice.domain.models

/**
 * Пользователь.
 *
 * @param username Имя пользователя (e-mail).
 * @param password Пароль.
 * @param confirmationUrl URL подтверждения.
 * @param name ФИО.
 * @param age Возраст.
 * @param height Рост.
 * @param weight Вес.
 * @param illnesses Заболевания.
 * @param symptoms Симптомы.
 */
data class User(
    val username: String,
    val password: String,
    val confirmationUrl: String,
    val name: String = "",
    val age: Int = -1,
    val height: Int = -1,
    val weight: Int = -1,
    val illnesses: List<Illness> = emptyList(),
    val symptoms: List<Symptom> = emptyList()
)
