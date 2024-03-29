package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.models.duration
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("RouteEntryDto: Поход.")
data class EntryItemDto(
    @ApiModelProperty("Дата похода")
    @JsonProperty("date")
    val date: Date,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Пройден ли маршрут.")
    @JsonProperty("passed")
    val passed: Boolean,
    @ApiModelProperty("ID маршрута.")
    @JsonProperty("route_id")
    val routeId: Int?,
    @ApiModelProperty("ID")
    @JsonProperty("id")
    val id: Int
)

fun Entry.toRouteEntryDto(route: Route?) = EntryItemDto(
    date = points.first().timestamp.toDate(),
    duration = points.duration().toDate(),
    passed = route?.let { finished(it) } ?: true,
    routeId = route?.id,
    id = id
)