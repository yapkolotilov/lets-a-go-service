package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Point
import me.kolotilov.letsagoservice.utils.toDateTime
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "point")
data class PointEntity(
        @Column(name = "latitude")
        val latitude: Double,
        @Column(name = "longitude")
        val longitude: Double,
        @Column(name = "timestamp")
        val timestamp: Date,
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id: Int
)

fun Point.toPointEntity() = PointEntity(
        latitude = latitude,
        longitude = longitude,
        timestamp = timestamp.toDate(),
        id = id
)

fun PointEntity.toPoint() = Point(
        latitude = latitude,
        longitude = longitude,
        timestamp = timestamp.toDateTime(),
        id = id
)
