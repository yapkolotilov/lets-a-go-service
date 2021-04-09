package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.*
import me.kolotilov.letsagoservice.domain.services.kiloCaloriesBurnt
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("RouteDetailsDto: Детали маршрута.")
class RouteDetailsDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Расстояние.")
    @JsonProperty("distance")
    val distance: Double,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Перепад высот.")
    @JsonProperty("altitude_delta")
    val altitudeDelta: Double,
    @ApiModelProperty("Средняя скорость.")
    @JsonProperty("speed")
    val speed: Double,
    @ApiModelProperty("Калорий сожжено.")
    @JsonProperty("kilocalories_burnt")
    val kilocaloriesBurnt: Int?,
    @ApiModelProperty("Сложность маршрута.")
    @JsonProperty("difficulty")
    val difficulty: Int?,
    @ApiModelProperty("Тип маршрута.")
    @JsonProperty("type")
    val type: Route.Type?,
    @ApiModelProperty("Покрытие маршрута.")
    @JsonProperty("ground")
    val ground: Route.Ground?,
    @ApiModelProperty("Походы.")
    @JsonProperty("entries")
    val entries: List<EntryDto>,
    @ApiModelProperty("ID")
    @JsonProperty("id")
    val id: Int,
)

fun Route.toRouteDetailsDto(user: User) = RouteDetailsDto(
    name = name,
    distance = points.distance(),
    duration = points.duration().toDate(),
    altitudeDelta = points.altitudeDelta(),
    speed = points.speed(),
    kilocaloriesBurnt = kiloCaloriesBurnt(user, type, points),
    difficulty = difficulty,
    type = type,
    ground = ground,
    entries = entries.map { it.toEntryDto() },
    id = id
)