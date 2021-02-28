package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route

@ApiModel("RoutePreviewDto: Превью маршрута.")
data class RoutePreviewDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Начальная точка маршрута.")
    @JsonProperty("start_point")
    val startPoint: PointDto,
    @ApiModelProperty("Тип маршрута.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Покрытие маршрута.")
    @JsonProperty("ground")
    val ground: Route.Ground?,
    @ApiModelProperty("Длина маршрута (м).")
    @JsonProperty("length")
    val length: Double,
    @ApiModelProperty("ID")
    @JsonProperty("id")
    val id: Int,
)

fun Route.toRoutePreviewDto() = RoutePreviewDto(
    name = name,
    startPoint = points.first().toPointDto(),
    type = type,
    ground = ground,
    length = length(),
    id = id
)