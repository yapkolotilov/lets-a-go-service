package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import io.swagger.v3.oas.annotations.Operation
import me.kolotilov.letsagoservice.configuration.authorization.JwtUtils
import me.kolotilov.letsagoservice.configuration.authorization.UserDetailsServiceImpl
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.presentation.input.LoginDto
import me.kolotilov.letsagoservice.presentation.output.TokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
class AuthorizationController(
    private val userService: UserService,
    private val authenticationManager: AuthenticationManager,
    @Qualifier(UserDetailsServiceImpl.QUALIFIER)
    private val userDetailsService: UserDetailsService,
    private val jwtUtils: JwtUtils,
) {

    @ApiOperation("Регистрация в приложении")
    @PostMapping("register")
    fun register(
        @ApiParam("Логин и пароль")
        @RequestBody request: LoginDto
    ) {
        userService.register(request.username, request.password)
    }

    @ApiOperation("Логин в приложении")
    @PostMapping("login")
    fun login(
        @ApiParam("Логин и пароль")
        @RequestBody request: LoginDto
    ): TokenDto {
        return try {
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))
            val userDetails = userDetailsService.loadUserByUsername(request.username)
            val jwt = jwtUtils.generateToken(userDetails)
            TokenDto(jwt)
        } catch (e: Exception) {
            throw ResponseStatusException(HttpStatus.UNAUTHORIZED, "Неправильные логин или пароль")
        }
    }

    @ApiOperation("Подтверждение e-mail")
    @GetMapping("confirmEmail/{url}")
    fun confirmEmail(@PathVariable("url") url: String): String {
        if (userService.confirmEmail(url))
            return "e-mail подтверждён"
        else
            return "Некорректный URL"
    }
}