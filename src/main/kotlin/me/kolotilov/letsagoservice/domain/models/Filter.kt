package me.kolotilov.letsagoservice.domain.models

import org.joda.time.Duration

/**
 * Фильтр маршрутов.
 *
 * @param maxLength Максимальная длина маршрута.
 * @param maxDuration Максимальная продолжительность маршрута.
 * @param typesAllowed Разрешённые типы маршрутов.
 * @param groundsAllowed Разрешённые типы покрытия.
 */
data class Filter(
        val maxLength: Double?,
        val maxDuration: Duration?,
        val typesAllowed: List<Route.Type>?,
        val groundsAllowed: List<Route.Ground>?,
        val id: Int
) {

    /**
     * Возвращает true, если маршрут удовлетворяет фильтру.
     *
     * @param route Маршрут.
     */
    fun matches(route: Route): Boolean {
        if (maxLength != null && route.length() > maxLength)
            return false
        if (maxDuration != null && route.duration() > maxDuration)
            return false
        if (typesAllowed != null && !typesAllowed.contains(route.type))
            return false
        if (groundsAllowed != null && !groundsAllowed.contains(route.ground))
            return false
        return true
    }
}