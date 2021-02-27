package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.kolotilov.letsagoservice.domain.services.IllnessService
import me.kolotilov.letsagoservice.domain.services.SymptomService
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.presentation.input.ChangePasswordDto
import me.kolotilov.letsagoservice.presentation.input.EditDetailsDto
import me.kolotilov.letsagoservice.presentation.output.UserDetailsDto
import me.kolotilov.letsagoservice.presentation.output.toUserDetailsDto
import org.springframework.web.bind.annotation.*

@Api("Личные данные юзера.")
@RestController
@RequestMapping("/details")
class DetailsController(
    private val userService: UserService,
    private val illnessService: IllnessService,
    private val symptomService: SymptomService
) {

    @ApiOperation("Возвращает данные о здоровье пользователя.")
    @GetMapping
    fun getDetails(): UserDetailsDto {
        return userService.getCurrentUser().toUserDetailsDto()
    }

    @ApiOperation("Редактирование данных о здоровье пользователя.")
    @PostMapping
    fun editDetails(
        @ApiParam("Данные о здоровье пользователя.")
        @RequestBody
        details: EditDetailsDto
    ): UserDetailsDto {
        return userService.edit(
            name = details.name,
            age = details.age,
            height = details.height,
            weight = details.weight,
            illnesses = details.illnesses?.let { illnessService.getOrCreateAll(details.illnesses) },
            symptoms = details.symptoms?.let { symptomService.getOrCreateAll(details.symptoms) },
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
}