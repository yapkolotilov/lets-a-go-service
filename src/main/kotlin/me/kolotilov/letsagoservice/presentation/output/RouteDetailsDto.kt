package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("RouteDetailsDto: Детали маршрута.")
class RouteDetailsDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Сложность маршрута.")
    @JsonProperty("difficulty")
    val difficulty: Int?,
    @ApiModelProperty("Точки маршрута.")
    @JsonProperty("points")
    val points: List<PointDto>,
    @ApiModelProperty("Тип маршрута.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Покрытие маршрута.")
    @JsonProperty("ground")
    val ground: Route.Ground?,
    @ApiModelProperty("Длительность маршрута.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Длина маршрута (м).")
    @JsonProperty("length")
    val length: Double,
    @ApiModelProperty("Походы.")
    @JsonProperty("entries")
    val entries: List<EntryDto>,
    @ApiModelProperty("ID")
    @JsonProperty("id")
    val id: Int
)

fun Route.toRouteDetailsDto() = RouteDetailsDto(
    name = name,
    difficulty = difficulty,
    points = points.map { it.toPointDto() },
    type = type,
    ground = ground,
    duration = duration().toDate(),
    entries = entries.map { it.toEntryDto() },
    length = length(),
    id = id
)