package me.kolotilov.letsagoservice.presentation.controllers

import me.kolotilov.letsagoservice.domain.services.MapService
import me.kolotilov.letsagoservice.persistance.repositories.RouteRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/test")
class TestController(
    private val mapService: MapService
) {

    @GetMapping("/time")
    fun time(): Date {
        return Date()
    }

    @PostMapping("/routes/clear")
    fun clearRoutes() {
        mapService.clearRoutes()
    }
}