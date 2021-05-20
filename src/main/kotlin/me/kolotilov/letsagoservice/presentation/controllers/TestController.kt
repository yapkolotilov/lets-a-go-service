package me.kolotilov.letsagoservice.presentation.controllers

import me.kolotilov.letsagoservice.domain.services.MapService
import me.kolotilov.letsagoservice.domain.services.UserService
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/test")
class TestController(
    private val mapService: MapService,
    private val userService: UserService
) {

    @GetMapping("/time")
    fun time(): Date {
        return Date()
    }

    @GetMapping("/wakeUp")
    fun wakeUp(): String {
        return "<h1>Я проснулся!<h1/>"
    }

    @DeleteMapping("users/clear")
    fun clearUsers() {
        mapService.clearUsers()
    }

    @DeleteMapping("users/clear/{username}")
    fun clearUser(@PathVariable("username") username: String) {
        userService.delete(username)
    }
}