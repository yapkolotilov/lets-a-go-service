package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.kolotilov.letsagoservice.configuration.ErrorCode
import me.kolotilov.letsagoservice.configuration.authorization.JwtUtils
import me.kolotilov.letsagoservice.configuration.authorization.UserDetailsServiceImpl
import me.kolotilov.letsagoservice.configuration.toServiceException
import me.kolotilov.letsagoservice.domain.services.AuthService
import me.kolotilov.letsagoservice.presentation.input.LoginDto
import me.kolotilov.letsagoservice.presentation.output.TokenDto
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.web.bind.annotation.*

@Api("Авторизация в приложении.")
@RestController
@RequestMapping("/auth")
class AuthorizationController(
    private val authService: AuthService,
    private val authenticationManager: AuthenticationManager,
    private val jwtUtils: JwtUtils
) {

    @Autowired
    @Qualifier(UserDetailsServiceImpl.QUALIFIER)
    private lateinit var userDetailsService: UserDetailsService

    @ApiOperation("Регистрация в приложении")
    @PostMapping("/register")
    fun register(
        @ApiParam("Логин и пароль")
        @RequestBody request: LoginDto
    ) {
        authService.register(request.username, request.password)
    }

    @ApiOperation("Логин в приложении")
    @PostMapping("/login")
    fun login(
        @ApiParam("Логин и пароль")
        @RequestBody request: LoginDto
    ): TokenDto {
        return try {
            val userDetails = userDetailsService.loadUserByUsername(request.username)
            authenticationManager.authenticate(UsernamePasswordAuthenticationToken(request.username, request.password))
            val jwt = jwtUtils.generateToken(userDetails)
            TokenDto(jwt)
        } catch (e: UsernameNotFoundException) {
            throw e.toServiceException(
                code = ErrorCode.USER_NOT_EXISTS,
                status = HttpStatus.UNAUTHORIZED,
                message = "Пользователя не существует",
            )
        } catch (e: AuthenticationException) {
            throw e.toServiceException(
                code = ErrorCode.INVALID_PASSWORD,
                status = HttpStatus.UNAUTHORIZED,
                message = "Неправильный пароль",
            )
        }
    }

    @ApiOperation("Подтверждение e-mail")
    @GetMapping("/confirm_email/{url}")
    fun confirmEmail(@PathVariable("url") url: String): String {
        return if (authService.confirmEmail(url))
            "e-mail подтверждён"
        else
            "Некорректный URL"
    }
}