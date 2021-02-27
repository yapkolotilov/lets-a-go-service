package me.kolotilov.letsagoservice.presentation.input

import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.utils.toDuration
import java.util.*
import javax.validation.constraints.Positive

@ApiModel("Фильтр.")
data class FilterDto(
    @Positive
    @ApiModelProperty("Максимальная длина маршрута (м).")
    val maxLength: Double?,
    @ApiModelProperty("Максимальная продолжительность маршрута")
    val maxDuration: Date?,
    @ApiModelProperty("Разрешённые типы маршрутов")
    val typesAllowed: List<Route.Type>?,
    @ApiModelProperty("Разрешённые покрытия маршрутов.")
    val groundsAllowed: List<Route.Ground>?
)

fun FilterDto.toFilter() = Filter(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDuration(),
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed,
    id = 0
)