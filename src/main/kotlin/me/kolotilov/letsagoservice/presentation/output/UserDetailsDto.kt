package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.presentation.input.FilterDto
import me.kolotilov.letsagoservice.presentation.input.toFilterDto
import org.joda.time.DateTime
import org.joda.time.Years
import java.util.*

@ApiModel("UserDetailsDto: Данные о пользователе")
data class UserDetailsDto(
    @ApiModelProperty("Имя пользователя.")
    @JsonProperty("username")
    val username: String,
    @ApiModelProperty("ФИО")
    @JsonProperty("name")
    val name: String?,
    @ApiModelProperty("Возраст.")
    @JsonProperty("age")
    val age: Int?,
    @ApiModelProperty("Дата рождения.")
    @JsonProperty("birthDate")
    val birthDate: Date?,
    @ApiModelProperty("Рост.")
    @JsonProperty("height")
    val height: Int?,
    @ApiModelProperty("Вес.")
    @JsonProperty("weight")
    val weight: Int? ,
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
    age = birthDate?.let { Years.yearsBetween(it, DateTime.now()) }?.years,
    birthDate = birthDate?.toDate(),
    height = height,
    weight = weight,
    illnesses = illnesses.map { it.name },
    symptoms = symptoms.map { it.name },
    filter = filter.toFilterDto()
)