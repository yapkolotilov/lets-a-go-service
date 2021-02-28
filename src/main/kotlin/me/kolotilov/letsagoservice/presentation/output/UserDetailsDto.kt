package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.presentation.input.FilterDto
import me.kolotilov.letsagoservice.presentation.input.toFilterDto

@ApiModel("UserDetailsDto: Данные о пользователе")
data class UserDetailsDto(
    @ApiModelProperty("Имя пользователя.")
    @JsonProperty("username")
    val username: String,
    @ApiModelProperty("ФИО")
    @JsonProperty("name")
    val name: String,
    @ApiModelProperty("Возраст.")
    @JsonProperty("age")
    val age: Int? = null,
    @ApiModelProperty("Рост.")
    @JsonProperty("height")
    val height: Int? = null,
    @ApiModelProperty("Вес.")
    @JsonProperty("weight")
    val weight: Int? = null,
    @ApiModelProperty("Болезни.")
    @JsonProperty("illnesses")
    val illnesses: List<String>,
    @ApiModelProperty("Симптомы.")
    @JsonProperty("symptoms")
    val symptoms: List<String>,
    @ApiModelProperty("Фильтр")
    @JsonProperty("filter")
    val filter: FilterDto
)

fun User.toUserDetailsDto() = UserDetailsDto(
    username = username,
    name = name,
    age = if (age > 0) age else null,
    height = if (height > 0) height else null,
    weight = if (weight > 0) weight else null,
    illnesses = illnesses.map { it.name },
    symptoms = symptoms.map { it.name },
    filter = filter.toFilterDto()
)