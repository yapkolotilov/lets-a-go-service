package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Entry

@ApiModel("EntryDto: Поход.")
data class EntryDto(
    @ApiModelProperty("Точки.")
    @JsonProperty("points")
    val points: List<PointDto>,
    @ApiModelProperty("ID.")
    @JsonProperty("id")
    val id: Int
)

fun Entry.toEntryDto() = EntryDto(
    points = points.map { it.toPointDto() },
    id = id
)