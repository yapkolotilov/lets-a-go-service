package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("CreateRoutePreviewDto: превью маршрута.")
data class CreateRoutePreviewDto(
    @ApiModelProperty("Точки маршрута.")
    @JsonProperty("points")
    val points: List<CreatePointDto>
)