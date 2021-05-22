package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route

@ApiModel("RouteLineDto: Маршрут на карте.")
data class RouteLineDto(
    @ApiModelProperty("Тип.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Точки.")
    @JsonProperty("points")
    val points: List<PointDto>,
    @ApiModelProperty("ID")
    @JsonProperty("id")
    val id: Int,
)

fun Route.toRouteLineDto() = RouteLineDto(
    type = type,
    points = points.map { it.toPointDto() },
    id = id
)