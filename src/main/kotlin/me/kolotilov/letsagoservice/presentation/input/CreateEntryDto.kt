package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Entry

@ApiModel("CreateEntryDto: Создание прохода.")
data class CreateEntryDto(
    @ApiModelProperty("Точки.")
    @JsonProperty("points")
    val points: List<CreatePointDto>
)

fun CreateEntryDto.toEntry() = Entry(
    points = points.map { it.toPoint() },
    id = -1
)