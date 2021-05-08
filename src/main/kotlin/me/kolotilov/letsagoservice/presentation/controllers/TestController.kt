package me.kolotilov.letsagoservice.presentation.controllers

import me.kolotilov.letsagoservice.domain.services.MapService
import me.kolotilov.letsagoservice.domain.services.UserService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
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

    @DeleteMapping("/routes/clear")
    fun clearRoutes() {
        mapService.clearRoutes()
    }

    @DeleteMapping("users/clear")
    fun clearUsers() {
        mapService.clearRoutes()
    }

    @GetMapping("/entries")
    fun getCurrentEntries(): Int {
        return userService.getCurrentUser().entries.size
    }
}