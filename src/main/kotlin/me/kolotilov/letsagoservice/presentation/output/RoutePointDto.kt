package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route

@ApiModel("RoutePointDto: маршрут на карте")
data class RoutePointDto(
    @ApiModelProperty("Тип.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Стартовая точка.")
    @JsonProperty("start_point")
    val startPoint: PointDto,
    @ApiModelProperty("ID.")
    @JsonProperty("id")
    val id: Int
)

fun Route.toRoutePointDto() = RoutePointDto(
    type = type,
    startPoint = points.first().toPointDto(),
    id = id
)