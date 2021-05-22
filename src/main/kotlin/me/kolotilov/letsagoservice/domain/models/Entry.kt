package me.kolotilov.letsagoservice.domain.models

import org.joda.time.DateTime
import org.joda.time.Duration

/**
 * Поход пользователя.
 *
 * @param points Точки маршрута.
 * @param id ID похода.
 */
data class Entry(
        val points: List<Point>,
        val id: Int
) {

        /**
         * Возвращает true, если поход был завершён.
         *
         * @param route Маршрут.
         */
        fun finished(route: Route): Boolean {
                return points.last().distance(route.points.last()) < 100
        }

        /**
         * Время начала маршрута.
         */
        fun startDate(): DateTime {
                return points.first().timestamp
        }

        /**
         * Продолжительность маршрута.
         */
        fun duration(): Duration {
                return points.duration()
        }

        /**
         * Длина маршрута.
         */
        fun distance(): Double {
                return points.distance()
        }
}