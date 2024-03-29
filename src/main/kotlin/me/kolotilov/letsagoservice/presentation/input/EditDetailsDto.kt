package me.kolotilov.letsagoservice.presentation.input

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import org.hibernate.validator.constraints.Range
import java.util.*
import javax.validation.constraints.NotEmpty

@ApiModel("PersonalHealthDto: Данные о здоровье пользователя.")
data class EditDetailsDto(
    @ApiModelProperty("ФИО.")
    @JsonProperty("name")
    @NotEmpty
    val name: String?,

    @ApiModelProperty("Возраст.")
    @JsonProperty("birthDate")
    val birthDate: Date?,

    @ApiModelProperty("Рост (см).")
    @JsonProperty("height")
    @Range(min = 50, max = 272)
    val height: Int?,

    @ApiModelProperty("Вес (кг).")
    @JsonProperty("weight")
    @Range(min = 2, max = 635)
    val weight: Int?,

    @ApiModelProperty("Заболевания (названия).")
    @JsonProperty("illnesses")
    val illnesses: List<String>?,

    @ApiModelProperty("Симптомы (названия).")
    @JsonProperty("symptoms")
    val symptoms: List<String>?,

    @ApiModelProperty("Фильтр.")
    @JsonProperty("filter")
    val filter: FilterDto?,

    @ApiModelProperty("Обновлять ли фильтр.")
    @JsonProperty("update_filter")
    val updateFilter: Boolean
)