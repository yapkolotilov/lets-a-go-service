package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Координаты области")
class CoordinatesDto(
    @ApiModelProperty("Левый верхний угол")
    @JsonProperty("topLeft")
    val topLeft: CoordinateDto,
    @ApiModelProperty("Правый нижний угол")
    @JsonProperty("bottomRight")
    val bottomRight: CoordinateDto
)