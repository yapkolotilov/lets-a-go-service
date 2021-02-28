package me.kolotilov.letsagoservice.domain.models

import org.joda.time.Duration
import kotlin.math.*

/**
 * Маршрут.
 *
 * @param name Название.
 * @param difficulty Сложность от 1 до 5.
 * @param type Тип маршрута.
 * @param ground Тип покрытия.
 * @param points Точки маршрута.
 * @param entries Походы пользователей.
 * @param id ID маршрута.
 */
data class Route(
        val name: String?,
        val difficulty: Int?,
        val type: Type?,
        val ground: Ground?,
        val points: List<Point>,
        val entries: List<Entry>,
        val id: Int,
) {

    /**
     * Тип маршрута.
     */
    enum class Type {
        /**
         * Ходьба.
         */
        WALKING,

        /**
         * Бег.
         */
        RUNNING,

        /**
         * Велосипед.
         */
        CYCLING
    }

    /**
     * Тип покрытия.
     */
    enum class Ground {
        /**
         * Асфальт.
         */
        ASPHALT,

        /**
         * Пересечённая местность.
         */
        TRACK
    }

    /**
     * Длина маршрута.
     */
    fun length(): Double {
        var result = 0.0
        for (i in 0..points.size - 2) {
            val p1 = points[i]
            val p2 = points[i + 1]
            result += distance(
                    lat1 = p1.latitude,
                    lat2 = p2.latitude,
                    lon1 = p1.longitude,
                    lon2 = p2.longitude
            )
        }
        return result
    }

    /**
     * Продолжительность маршрута.
     */
    fun duration(): Duration {
        val validEntries = entries.filter { it.finished }
        val midDuration = validEntries.sumByDouble { it.duration.millis.toDouble() }.toLong() /
                (validEntries.size.takeIf { it != 0 } ?: 1)
        return Duration(midDuration)
    }

    // https://stackoverflow.com/a/16794680
    private fun distance(lat1: Double, lat2: Double, lon1: Double, lon2: Double): Double {
        val el1 = 0.0
        val el2 = 0.0
        val R = 6371 // Radius of the earth
        val latDistance = Math.toRadians(lat2 - lat1)
        val lonDistance = Math.toRadians(lon2 - lon1)
        val a = (sin(latDistance / 2) * sin(latDistance / 2)
                + (cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
                * sin(lonDistance / 2) * sin(lonDistance / 2)))
        val c = 2 * atan2(sqrt(a), sqrt(1 - a))
        var distance = R * c * 1000 // convert to meters
        val height = el1 - el2
        distance = distance.pow(2.0) + height.pow(2.0)
        return sqrt(distance)
    }
}

