package me.kolotilov.letsagoservice.presentation.output

import com.fasterxml.jackson.annotation.JsonProperty
import io.swagger.annotations.ApiModel
import io.swagger.annotations.ApiModelProperty
import me.kolotilov.letsagoservice.domain.models.Point
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.domain.services.kiloCaloriesBurnt
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
    val filter: FilterDto,
    @ApiModelProperty("Всего расстояния пройдено.")
    @JsonProperty("total_distance")
    val totalDistance: Double,
    @ApiModelProperty("Всего калорий сожжено.")
    @JsonProperty("total_calories_burnt")
    val totalKilocaloriesBurnt: Int?,
    @ApiModelProperty("Маршруты.")
    @JsonProperty("routes")
    val routes: List<RouteItemDto>,
    @ApiModelProperty("Походы.")
    @JsonProperty("entries")
    val entries: List<EntryItemDto>,
)

fun User.toUserDetailsDto(userLocation: Point?) = UserDetailsDto(
    username = username,
    name = name.takeIf { !it.isNullOrEmpty() },
    age = birthDate?.let { Years.yearsBetween(it, DateTime.now()) }?.years,
    birthDate = birthDate?.toDate(),
    height = height,
    weight = weight,
    illnesses = illnesses.map { it.name },
    symptoms = symptoms.map { it.name },
    filter = filter.toFilterDto(),
    totalDistance = entries.sumByDouble { it.distance() },
    totalKilocaloriesBurnt = entries.sumBy { entry ->
        val route = routes.firstOrNull { it.entries.any { it.id == entry.id } }
        kiloCaloriesBurnt(this, route?.type, entry.points) ?: 0
    }.takeIf { it != 0 },
    routes = routes.map { it.toRouteItemDto(userLocation) },
    entries = entries.map { entry ->
        val route = routes.firstOrNull { it.entries.any { it.id == entry.id } }
        entry.toRouteEntryDto(route)
    }
)