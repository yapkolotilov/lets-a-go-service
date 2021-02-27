package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("EntryDto: Поход.")
data class EntryDto(
    @ApiModelProperty("Имя пользователя.")
    @JsonProperty("username")
    val username: String,
    @ApiModelProperty("Маршрут.")
    @JsonProperty("route")
    val route: RoutePreviewDto,
    @ApiModelProperty("Время начала.")
    @JsonProperty("timestamp")
    val timestamp: Date,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Закончен ли поход.")
    @JsonProperty("finished")
    val finished: Boolean,
    @ApiModelProperty("ID.")
    @JsonProperty("id")
    val id: Int
)

fun Entry.toEntryDto() = EntryDto(
    username = user.username,
    route = route.toRoutePreviewDto(user, null),
    timestamp = timestamp.toDate(),
    duration = duration.toDate(),
    finished = finished,
    id = id
)