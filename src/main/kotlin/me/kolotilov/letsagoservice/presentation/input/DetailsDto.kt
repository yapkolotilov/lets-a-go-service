package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("DetailsDto: запрос информации о личном кабинете")
data class DetailsDto(
    @ApiModelProperty("Позиция пользователя")
    @JsonProperty("user_location")
    val userLocation: CreatePointDto?
)
