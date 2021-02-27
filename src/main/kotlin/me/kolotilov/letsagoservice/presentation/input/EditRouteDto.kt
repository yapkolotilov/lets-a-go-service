package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route

@ApiModel("EditRouteDto: Редактирование маршрута.")
data class EditRouteDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Сложность.")
    @JsonProperty("difficulty")
    val difficulty: Int?,
    @ApiModelProperty("Тип.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Тип покрытия.")
    @JsonProperty("ground")
    val ground: Route.Ground?
)

fun EditRouteDto.toRoute(route: Route) = route.copy(
    name = name ?: route.name,
    difficulty = difficulty ?: route.difficulty,
    type = type ?: route.type,
    ground = ground ?: route.ground
)