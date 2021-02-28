package me.kolotilov.letsagoservice.presentation.controllers

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.*

@RestController
@RequestMapping("/test")
class TestController {

    @GetMapping("/time")
    fun time(): Date {
        return Date()
    }
}