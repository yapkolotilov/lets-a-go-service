package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.kolotilov.letsagoservice.domain.models.Point
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.domain.services.IllnessService
import me.kolotilov.letsagoservice.domain.services.MapService
import me.kolotilov.letsagoservice.domain.services.SymptomService
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.presentation.input.*
import me.kolotilov.letsagoservice.presentation.output.UserDetailsDto
import me.kolotilov.letsagoservice.presentation.output.toUserDetailsDto
import me.kolotilov.letsagoservice.utils.toDateTime
import org.springframework.web.bind.annotation.*

@Api("Личные данные юзера.")
@RestController
@RequestMapping("/details")
class DetailsController(
    private val userService: UserService,
    private val illnessService: IllnessService,
    private val symptomService: SymptomService,
    private val mapService: MapService
) {

    @ApiOperation("Возвращает данные о здоровье пользователя.")
    @PostMapping
    fun getDetails(@RequestBody userLocation: DetailsDto): UserDetailsDto {
        return userService.getCurrentUser().toUserDetailsDto(userLocation?.userLocation?.toPoint())
    }

    @ApiOperation("Редактирование данных о здоровье пользователя.")
    @PostMapping("/edit")
    fun editDetails(
        @ApiParam("Данные о здоровье пользователя.")
        @RequestBody
        details: EditDetailsDto
    ): UserDetailsDto {
        return userService.edit(
            name = details.name,
            birthDate = details.birthDate?.toDateTime(),
            height = details.height,
            weight = details.weight,
            illnesses = details.illnesses?.let { illnessService.getOrCreateAll(details.illnesses) },
            symptoms = details.symptoms?.let { symptomService.getOrCreateAll(details.symptoms) },
            filter = details.filter?.toFilter(),
            updateFilter = details.updateFilter,
        ).toUserDetailsDto()
    }

    @ApiOperation("Смена пароля.")
    @GetMapping("/change_password")
    fun changePassword(
        @ApiParam("Новый пароль.")
        @RequestBody
        passwordDto: ChangePasswordDto
    ): UserDetailsDto {
        return userService.changePassword(passwordDto.password).toUserDetailsDto()
    }

    @ApiOperation("Возвращает список всех доступных заболеваний.")
    @GetMapping("/illnesses")
    fun getIllnesses(): List<String> {
        return illnessService.getAllApproved().map { it.name }
    }

    @ApiOperation("Возвращает список всех доступных симптомов.")
    @GetMapping("/symptoms")
    fun getSymptoms(): List<String> {
        return symptomService.getAllApproved().map { it.name }
    }

    private fun User.toUserDetailsDto(userLocation: Point? = null) = toUserDetailsDto(mapService.getAllRoutes(false), userLocation = userLocation)
}