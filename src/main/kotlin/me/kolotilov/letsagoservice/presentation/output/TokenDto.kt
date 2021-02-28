package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

data class TokenDto(
    @ApiModelProperty("Токен")
    @JsonProperty("token")
    val token: String
)
