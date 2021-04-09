package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.models.RoutePreview
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("RoutePreviewDto: превью маршрута.")
data class RoutePreviewDto(
    @ApiModelProperty("Расстояние.")
    @JsonProperty("distance")
    val distance: Double,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Скорость (км/ч).")
    @JsonProperty("speed")
    val speed: Double,
    @ApiModelProperty("Сожжёные килокалории.")
    @JsonProperty("caloriesBurnt")
    val kiloCaloriesBurnt: Int?,
    @ApiModelProperty("Перепад высот.")
    @JsonProperty("altitudeDelta")
    val altitudeDelta: Double,
    @ApiModelProperty("Тип.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Сложность")
    @JsonProperty("difficulty")
    val difficulty: Int?
)

fun RoutePreview.toRoutePreviewDto() = RoutePreviewDto(
    distance = distance,
    duration = duration.toDate(),
    speed = speed,
    kiloCaloriesBurnt = kiloCaloriesBurnt,
    altitudeDelta = altitudeDelta,
    type = type,
    difficulty = difficulty
)