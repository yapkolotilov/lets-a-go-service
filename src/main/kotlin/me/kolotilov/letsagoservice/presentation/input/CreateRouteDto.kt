package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.domain.models.Route
import javax.validation.constraints.NotEmpty

@ApiModel("CreateRouteDto: Создание маршрута.")
data class CreateRouteDto(
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
    val ground: Route.Ground?,
    @ApiModelProperty("Точки.")
    @JsonProperty("points")
    @NotEmpty
    val points: List<CreatePointDto>
)

fun CreateRouteDto.toRoute() = Route(
    name = name,
    difficulty = difficulty,
    type = type,
    ground = ground,
    points = points.map { it.toPoint() },
    entries = listOf(
        Entry(
            points = points.map { it.toPoint() },
            id = 0
        )
    ),
    id = 0
)