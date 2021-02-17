package me.kolotilov.letsagoservice.domain.models

/**
 * Симптом.
 *
 * @param name Название.
 * @param approved Одобрен ли симптом.
 */
data class Symptom(
    val name: String,
    val approved: Boolean
)
