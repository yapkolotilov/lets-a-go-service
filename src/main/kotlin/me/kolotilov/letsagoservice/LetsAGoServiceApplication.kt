package me.kolotilov.letsagoservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class LetsAGoServiceApplication

fun main(args: Array<String>) {
    runApplication<LetsAGoServiceApplication>(*args)
}
