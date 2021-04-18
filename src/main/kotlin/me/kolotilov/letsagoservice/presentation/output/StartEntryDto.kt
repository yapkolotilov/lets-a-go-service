package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route

@ApiModel("StartEntryDto: Начать поход.")
data class StartEntryDto(
    @ApiModelProperty("ID маршрута.")
    @JsonProperty("id")
    val id: Int,
    @ApiModelProperty("Название маршрута.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Точки маршрута.")
    @JsonProperty("points")
    val points: List<PointDto>,
)

fun Route.toStartEntryDto() = StartEntryDto(
    id = id,
    name = name,
    points = points.map { it.toPointDto() }
)