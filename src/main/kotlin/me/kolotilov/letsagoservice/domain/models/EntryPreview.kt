package me.kolotilov.letsagoservice.domain.models

import org.joda.time.Duration

/**
 * Данные для предпросмотра похода.
 *
 * @param distance Длина маршрута.
 * @param duration Продолжительность маршрута.
 * @param speed Средняя скорость.
 * @param kiloCaloriesBurnt Кол-во сожжёных килокалорий.
 * @param altitudeDelta Перепад высот в маршруте.
 * @param passed Пройден ли маршрут до конца.
 * @param routeId ID маршрута.
 */
data class EntryPreview(
    val distance: Double,
    val duration: Duration,
    val speed: Double,
    val kiloCaloriesBurnt: Int?,
    val altitudeDelta: Double,
    val passed: Boolean,
    val routeId: Int
)
