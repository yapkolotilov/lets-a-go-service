package me.kolotilov.letsagoservice

import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow

@DisplayName("Application")
class ApplicationTests : BaseTests() {

    @Test
    @DisplayName("Initialize context")
    fun contextLoads() {
        assertDoesNotThrow {
            mapService.getAllRoutes(false)
        }
    }
}