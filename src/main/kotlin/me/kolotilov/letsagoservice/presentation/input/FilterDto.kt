package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.utils.toDate
import me.kolotilov.letsagoservice.utils.toDuration
import java.util.*
import javax.validation.constraints.Positive

@ApiModel("Фильтр.")
data class FilterDto(
    @Positive
    @ApiModelProperty("Минимальная длина маршрута (м).")
    @JsonProperty("min_length")
    val minLength: Double?,
    @Positive
    @ApiModelProperty("Максимальная длина маршрута (м).")
    @JsonProperty("max_length")
    val maxLength: Double?,
    @ApiModelProperty("Минимальная продолжительность маршрута")
    @JsonProperty("min_duration")
    val minDuration: Date?,
    @ApiModelProperty("Максимальная продолжительность маршрута")
    @JsonProperty("max_duration")
    val maxDuration: Date?,
    @ApiModelProperty("Разрешённые типы маршрутов")
    @JsonProperty("types_allowed")
    val typesAllowed: List<Route.Type>?,
    @ApiModelProperty("Разрешённые покрытия маршрутов.")
    @JsonProperty("grounds_allowed")
    val groundsAllowed: List<Route.Ground>?,
    @ApiModelProperty("Включён ли фильтр.")
    @JsonProperty("enabled")
    val enabled: Boolean
)

fun FilterDto.toFilter() = Filter(
    length = if (minLength != null && maxLength != null) minLength..maxLength else null,
    duration = if (minDuration != null && maxDuration != null) minDuration.toDuration()..maxDuration.toDuration() else null,
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed,
    enabled = enabled,
    id = 0
)

fun Filter.toFilterDto() = FilterDto(
    minLength = length?.start,
    maxLength = length?.endInclusive,
    minDuration = duration?.start?.toDate(),
    maxDuration = duration?.endInclusive?.toDate(),
    typesAllowed = typesAllowed?.takeIf { it.isNotEmpty() },
    groundsAllowed = groundsAllowed?.takeIf { it.isNotEmpty() },
    enabled = enabled
)