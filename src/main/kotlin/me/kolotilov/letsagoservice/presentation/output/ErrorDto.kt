package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.configuration.ErrorCode
import me.kolotilov.letsagoservice.configuration.ServiceException

@ApiModel("ErrorDto: Ошибка.")
data class ErrorDto(
    @ApiModelProperty("Код ошибки.")
    @JsonProperty("code")
    val code: ErrorCode,
    @ApiModelProperty("Статус ошибки.")
    @JsonProperty("status")
    val status: Int,
    @ApiModelProperty("Сообщение об ощибке.")
    @JsonProperty("message")
    val message: String,
    @ApiModelProperty("Трассировка стека.")
    @JsonProperty("stackTrace")
    val stackTrace: String
)

fun ServiceException.toErrorDto() = ErrorDto(
    code = code,
    status = status.value(),
    message = message,
    stackTrace = stackTrace
)