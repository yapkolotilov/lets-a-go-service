package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Point
import java.util.*

@ApiModel("PointDto: Точка маршрута.")
data class PointDto(
    @ApiModelProperty("Широта.")
    @JsonProperty("latitude")
    val latitude: Double,
    @ApiModelProperty("Долгота.")
    @JsonProperty("longitude")
    val longitude: Double,
    @ApiModelProperty("Высота.")
    @JsonProperty("altitude")
    val altitude: Double,
    @ApiModelProperty("Время.")
    @JsonProperty("timestamp")
    val timestamp: Date,
    @ApiModelProperty("ID точки.")
    @JsonProperty("id")
    val id: Int
)

fun Point.toPointDto() = PointDto(
    latitude = latitude,
    longitude = longitude,
    altitude = altitude,
    timestamp = timestamp.toDate(),
    id = id
)