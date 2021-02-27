package me.kolotilov.letsagoservice.domain.models

import org.joda.time.DateTime
import org.joda.time.Duration

/**
 * Поход пользователя.
 *
 * @param user Пользователь.
 * @param route Маршрут.
 * @param timestamp Время похода.
 * @param duration Продолжительность похода.
 * @param finished true, если пользователь дошёл до конца маршрута.
 * @param id ID похода.
 */
data class Entry(
        val user: User,
        val route: Route,
        val timestamp: DateTime,
        val duration: Duration,
        val finished: Boolean,
        val id: Int
)