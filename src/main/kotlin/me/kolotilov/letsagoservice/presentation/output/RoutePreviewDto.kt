package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.models.User

@ApiModel("RoutePreviewDto: Превью маршрута.")
data class RoutePreviewDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Начальная точка маршрута.")
    @JsonProperty("start_point")
    val startPoint: PointDto,
    @ApiModelProperty("true, если это маршрут пользователя.")
    @JsonProperty("mine")
    val mine: Boolean,
    @ApiModelProperty("true, если маршрут подходит по фильтрам.")
    @JsonProperty("filtered")
    val filtered: Boolean,
    @ApiModelProperty("Тип маршрута.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Покрытие маршрута.")
    @JsonProperty("ground")
    val ground: Route.Ground?,
    @ApiModelProperty("ID")
    @JsonProperty("id")
    val id: Int = 0,
)

fun Route.toRoutePreviewDto(user: User, filter: Filter?) = RoutePreviewDto(
    name = name,
    startPoint = points.first().toPointDto(),
    mine = owner == user,
    filtered = filter?.matches(this) ?: true,
    type = type,
    ground = ground,
    id = id
)