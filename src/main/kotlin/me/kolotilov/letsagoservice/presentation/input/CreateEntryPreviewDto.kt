package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("CreateEntryPreviewDto: Превью прохода.")
data class CreateEntryPreviewDto(
    @ApiModelProperty("ID маршрута.")
    @JsonProperty("route_id")
    val routeId: Int,
    @ApiModelProperty("Точки.")
    @JsonProperty("points")
    val points: List<CreatePointDto>
)
