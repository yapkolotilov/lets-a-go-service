package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty

@ApiModel("ChangePasswordDto: Смена пароля.")
data class ChangePasswordDto(
    @ApiModelProperty("Новый пароль.")
    @JsonProperty("password")
    val password: String
)
