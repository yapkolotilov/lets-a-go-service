package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Point
import me.kolotilov.letsagoservice.utils.toDateTime
import java.util.*

@ApiModel("CreatePointDto: Создание точки.")
data class CreatePointDto(
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
    val timestamp: Date
)

fun CreatePointDto.toPoint() = Point(
    latitude = latitude,
    longitude = longitude,
    altitude = altitude,
    timestamp = timestamp.toDateTime(),
    id = 0
)