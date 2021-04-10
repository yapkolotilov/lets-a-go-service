package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.EntryPreview
import me.kolotilov.letsagoservice.utils.toDate
import java.util.*

@ApiModel("EntryPreviewDto: превью прохода.")
data class EntryPreviewDto(
    @ApiModelProperty("Расстояние.")
    @JsonProperty("distance")
    val distance: Double,
    @ApiModelProperty("Продолжительность.")
    @JsonProperty("duration")
    val duration: Date,
    @ApiModelProperty("Скорость (км/ч).")
    @JsonProperty("speed")
    val speed: Double,
    @ApiModelProperty("Сожжёные килокалории.")
    @JsonProperty("caloriesBurnt")
    val kiloCaloriesBurnt: Int?,
    @ApiModelProperty("Перепад высот.")
    @JsonProperty("altitudeDelta")
    val altitudeDelta: Double,
    @ApiModelProperty("Пройден ли маршрут.")
    @JsonProperty("passed")
    val passed: Boolean
)

fun EntryPreview.toEntryPreviewDto() = EntryPreviewDto(
    distance = distance,
    duration = duration.toDate(),
    speed = speed,
    kiloCaloriesBurnt = kiloCaloriesBurnt,
    altitudeDelta = altitudeDelta,
    passed = passed
)