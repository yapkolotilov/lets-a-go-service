package me.kolotilov.letsagoservice.domain.models

/**
 * Симптом.
 *
 * @param name Название.
 * @param approved Одобрен ли симптом.
 * @param filter Фильтр.
 */
data class Symptom(
    val name: String,
    val approved: Boolean,
    val filter: Filter?
)
