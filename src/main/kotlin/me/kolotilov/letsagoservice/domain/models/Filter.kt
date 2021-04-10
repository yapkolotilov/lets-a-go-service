package me.kolotilov.letsagoservice.domain.models

import org.joda.time.Duration

/**
 * Фильтр маршрутов.
 *
 * @param length Максимальная длина маршрута (км).
 * @param duration Максимальная продолжительность маршрута.
 * @param typesAllowed Разрешённые типы маршрутов.
 * @param groundsAllowed Разрешённые типы покрытия.
 */
data class Filter(
    val length: ClosedFloatingPointRange<Double>?,
    val duration: ClosedRange<Duration>?,
    val typesAllowed: List<Route.Type>?,
    val groundsAllowed: List<Route.Ground>?,
    val enabled: Boolean,
    val id: Int
) {

    companion object {

        /**
         * Возвращает логическое пересечение фильтров.
         */
        fun compose(vararg filters: Filter): Filter {
            val minLength= filters.mapNotNull { it.length?.start }.maxOrNull()
            val maxLength = filters.mapNotNull { it.length?.endInclusive }.minOrNull()

            val minDuration = filters.mapNotNull { it.duration?.start }.maxOrNull()
            val maxDuration = filters.mapNotNull { it.duration?.endInclusive }.minOrNull()

            return Filter(
                length = if (minLength != null && maxLength != null) minLength..maxLength else null,
                duration = if (minDuration != null && maxDuration != null) minDuration..maxDuration else null,
                typesAllowed = filters.mapNotNull { it.typesAllowed }.flatten().nullIfEmpty()?.distinct(),
                groundsAllowed = filters.mapNotNull { it.groundsAllowed }.flatten().nullIfEmpty()?.distinct(),
                enabled = true,
                id = -1
            )
        }

        private fun <T> List<T>.nullIfEmpty(): List<T>? {
            return if (isEmpty()) null else this
        }
    }

    /**
     * Возвращает true, если маршрут удовлетворяет фильтру.
     *
     * @param route Маршрут.
     */
    fun matches(route: Route): Boolean {
        if (!enabled)
            return true
        if (length != null && route.length() !in length)
            return false
        if (duration != null && route.duration() !in duration)
            return false
        if (typesAllowed != null && !typesAllowed.contains(route.type))
            return false
        if (groundsAllowed != null && !groundsAllowed.contains(route.ground))
            return false
        return true
    }
}