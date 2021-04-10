package me.kolotilov.letsagoservice.domain.models

import org.joda.time.Duration

data class EntryPreview(
    val distance: Double,
    val duration: Duration,
    val speed: Double,
    val kiloCaloriesBurnt: Int?,
    val altitudeDelta: Double,
    val passed: Boolean,
    val routeId: Int
)
