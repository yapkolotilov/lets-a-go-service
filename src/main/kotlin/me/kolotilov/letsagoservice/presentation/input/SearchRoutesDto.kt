package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("Поиск маршрута.")
data class SearchRoutesDto(
    @ApiModelProperty("Название.")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Фильтр.")
    @JsonProperty("filter")
    val filter: FilterDto?,
    @ApiModelProperty("Локация пользователя.")
    @JsonProperty("user_location")
    val userLocation: CreatePointDto?
)