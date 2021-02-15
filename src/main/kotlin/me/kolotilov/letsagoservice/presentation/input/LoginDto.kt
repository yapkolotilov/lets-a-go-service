package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModelProperty

data class LoginDto(
    @ApiModelProperty("Имя пользователя (e-mail)")
    @JsonProperty("username")
    val username: String,
    @ApiModelProperty("Пароль")
    @JsonProperty("password")
    val password: String
)
