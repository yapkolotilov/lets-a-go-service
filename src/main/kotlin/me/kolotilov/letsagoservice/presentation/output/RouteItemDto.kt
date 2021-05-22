package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Point
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("RouteItemDto: путь в списке.")
data class RouteItemDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Тип.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Покрытие.")
    @JsonProperty("ground")
    val ground: Route.Ground?,
    @ApiModelProperty("Длина.")
    @JsonProperty("distance")
    val distance: Double,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Стартовая локация.")
    @JsonProperty("distance_to_route")
    val distanceToRoute: Double?,
    @ApiModelProperty("ID.")
    @JsonProperty("id")
    val id: Int
)

fun Route.toRouteItemDto(userLocation: Point?) = RouteItemDto(
    name = name,
    type = type,
    ground = ground,
    distance = length(),
    duration = duration().toDate(),
    distanceToRoute = userLocation?.let { points.first() distance it },
    id = id
)