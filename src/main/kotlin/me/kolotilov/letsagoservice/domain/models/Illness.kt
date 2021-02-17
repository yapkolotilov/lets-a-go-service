package me.kolotilov.letsagoservice.domain.models

/**
 * Диагностированное заболевание.
 *
 * @param name Название.
 * @param symptoms Симптомы.
 * @param id ID.
 */
data class Illness(
    val name: String,
    val approved: Boolean,
    val symptoms: List<Symptom>
)