package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.utils.toDateTime
import me.kolotilov.letsagoservice.utils.toDuration
import java.util.*

@ApiModel("CreateEntryDto: Создание прохода.")
data class CreateEntryDto(
    @ApiModelProperty("Время начала.")
    @JsonProperty("timestamp")
    val timestamp: Date,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Закончен ли маршрут.")
    @JsonProperty("finished")
    val finished: Boolean
)

fun CreateEntryDto.toEntry() = Entry(
    timestamp = timestamp.toDateTime(),
    duration = duration.toDuration(),
    finished = finished,
    id = 0
)