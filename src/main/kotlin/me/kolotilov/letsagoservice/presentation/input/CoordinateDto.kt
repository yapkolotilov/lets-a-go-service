package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Координата на карте")
data class CoordinateDto(
    @ApiModelProperty("Широта")
    @JsonProperty("latitude")
    val latitude: Long,
    @ApiModelProperty("Долгота")
    @JsonProperty("longitude")
    val longitude: Long
)