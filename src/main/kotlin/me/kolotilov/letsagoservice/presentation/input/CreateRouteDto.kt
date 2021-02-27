package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.presentation.output.PointDto
import me.kolotilov.letsagoservice.presentation.output.toPoint

@ApiModel("CreateRouteDto: Создание маршрута.")
data class CreateRouteDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Сложность.")
    @JsonProperty("difficulty")
    val difficulty: Int,
    @ApiModelProperty("Тип.")
    @JsonProperty("type")
    val type: Route.Type,
    @ApiModelProperty("Тип покрытия.")
    @JsonProperty("ground")
    val ground: Route.Ground,
    @ApiModelProperty("Точки.")
    @JsonProperty("points")
    val points: List<PointDto>
)

fun CreateRouteDto.toRoute(owner: User) = Route(
    name = name,
    difficulty = difficulty,
    type = type,
    ground = ground,
    points = points.map { it.toPoint() },
    entries = emptyList(),
    owner = owner
)