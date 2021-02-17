package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.kolotilov.letsagoservice.domain.services.IllnessService
import me.kolotilov.letsagoservice.domain.services.SymptomService
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.presentation.input.ChangePasswordDto
import me.kolotilov.letsagoservice.presentation.input.EditHealthDto
import me.kolotilov.letsagoservice.presentation.output.UserDetailsDto
import me.kolotilov.letsagoservice.presentation.output.toUserDetailsDto
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UsersController(
    private val userService: UserService,
    private val illnessService: IllnessService,
    private val symptomService: SymptomService
) {

    @ApiOperation("Смена пароля.")
    @GetMapping("/changePassword")
    fun changePassword(
        @ApiParam("Новый пароль.")
        @RequestBody
        passwordDto: ChangePasswordDto
    ): UserDetailsDto {
        return userService.changePassword(passwordDto.password).toUserDetailsDto()
    }

    @ApiOperation("Возвращает данные о здоровье пользователя.")
    @GetMapping("/health")
    fun getHealth(): UserDetailsDto {
        return userService.getCurrentUser().toUserDetailsDto()
    }

    @ApiOperation("Редактирование данных о здоровье пользователя.")
    @PostMapping("/health")
    fun editHealth(
        @ApiParam("Данные о здоровье пользователя.")
        @RequestBody
        health: EditHealthDto
    ): UserDetailsDto {
        return userService.edit(
            name = health.name,
            age = health.age,
            height = health.height,
            weight = health.weight,
            illnesses = health.illnesses?.let { illnessService.getOrCreateAll(health.illnesses) },
            symptoms = health.symptoms?.let { symptomService.getOrCreateAll(health.symptoms) },
        ).toUserDetailsDto()
    }
}