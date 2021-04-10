package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.*
import me.kolotilov.letsagoservice.domain.services.kiloCaloriesBurnt
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("EntryDetailsDto: Поход.")
data class EntryDetailsDto(
    @ApiModelProperty("Закончен ли.")
    @JsonProperty("finished")
    val finished: Boolean,
    @ApiModelProperty("Дата.")
    @JsonProperty("date")
    val date: Date,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Пройденное расстояние.")
    @JsonProperty("distance")
    val distance: Double,
    @ApiModelProperty("Перепад высот.")
    @JsonProperty("altitude_delta")
    val altitudeDelta: Double,
    @ApiModelProperty("Скорость.")
    @JsonProperty("speed")
    val speed: Double,
    @ApiModelProperty("Ккал сожжено.")
    @JsonProperty("kilocalories_burnt")
    val kiloCaloriesBurnt: Int?,
    @ApiModelProperty("ID маршрута.")
    @JsonProperty("route_id")
    val routeId: Int,
    @ApiModelProperty("ID.")
    @JsonProperty("id")
    val id: Int
)

fun Entry.toEntryDetailsDto(route: Route, user: User) = EntryDetailsDto(
    finished = finished(route),
    date = startDate().toDate(),
    duration = duration().toDate(),
    distance = distance(),
    kiloCaloriesBurnt = kiloCaloriesBurnt(user, route.type, points),
    routeId = route.id,
    altitudeDelta = points.altitudeDelta(),
    speed = points.speed(),
    id = id
)