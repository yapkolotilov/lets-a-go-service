package me.kolotilov.letsagoservice.domain.models

import org.joda.time.DateTime

/**
 * Точка маршрута.
 *
 * @param latitude Широта.
 * @param longitude Долгота.
 * @param timestamp Время прохождения точки.
 * @param id ID точки.
 */
data class Point(
        val latitude: Double,
        val longitude: Double,
        val timestamp: DateTime,
        val id: Int
)
