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
    @ApiModelProperty("Максимальная длина маршрута (м).")
    @JsonProperty("max_length")
    val maxLength: Double?,
    @ApiModelProperty("Максимальная продолжительность маршрута")
    @JsonProperty("max_duration")
    val maxDuration: Date?,
    @ApiModelProperty("Разрешённые типы маршрутов")
    @JsonProperty("types_allowed")
    val typesAllowed: List<Route.Type>?,
    @ApiModelProperty("Разрешённые покрытия маршрутов.")
    @JsonProperty("grounds_allowed")
    val groundsAllowed: List<Route.Ground>?
)

fun FilterDto.toFilter() = Filter(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDuration(),
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed,
    id = 0
)

fun Filter.toFilterDto() = FilterDto(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDate(),
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed
)