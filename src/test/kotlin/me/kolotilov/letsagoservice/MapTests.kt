package me.kolotilov.letsagoservice

import me.kolotilov.letsagoservice.configuration.ServiceException
import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.domain.models.Point
import me.kolotilov.letsagoservice.domain.models.Route
import org.joda.time.DateTime
import org.junit.jupiter.api.*
import kotlin.test.assertTrue

class MapTests : BaseTests() {

    @Nested
    @DisplayName("Routes")
    inner class Routes {

        @Test
        @DisplayName("Getting routes")
        fun getAllRoutes() {
            val unfiltered = mapService.getAllRoutes(false)
            val filtered = mapService.getAllRoutes(true)
            assertTrue { unfiltered.size >= filtered.size }
        }

        @Test
        @DisplayName("Getting route")
        fun getRoute() {
            assertThrows<Exception> { mapService.getRoute(0) }
        }

        @Test
        @DisplayName("Finding routes")
        fun findRoutes() {
            assertDoesNotThrow { mapService.findRoutes(name = "bruh", filter = null) }
        }

        @Test
        @DisplayName("Creating route")
        fun createRoute() {
            assertThrows<NoSuchElementException> {
                mapService.createRoute(
                    Route(
                        name = null,
                        difficulty = 0,
                        ground = null,
                        type = null,
                        points = emptyList(),
                        entries = emptyList(),
                        isPublic = false,
                        id = -1
                    )
                )
            }
        }

        @Test
        @DisplayName("Edititng route")
        fun editRoute() {
            assertDoesNotThrow {
                mapService.editRoute(
                    Route(
                        name = null,
                        difficulty = 0,
                        ground = null,
                        type = null,
                        points = emptyList(),
                        entries = emptyList(),
                        isPublic = false,
                        id = -1
                    )
                )
            }
        }

        @Test
        @DisplayName("Deleting route")
        fun deleteRoute() {
            assertThrows<Exception> {
                mapService.deleteRoute(1)
            }
        }


    }

    @Nested
    @DisplayName("Entries")
    inner class Entries {

        @Test
        @DisplayName("Create entry")
        fun createEntry() {
            assertThrows<Exception> {
                mapService.createEntry(0, Entry(emptyList(), -1))
            }
        }

        @Test
        @DisplayName("Delete entry")
        fun deleteEntry() {
            assertThrows<NoSuchElementException> {
                mapService.deleteEntry(0)
            }
        }

        @Test
        @DisplayName("Get all entries")
        fun getAllEntries() {
            assertTrue {
                mapService.getAllEntries().size >= userService.getCurrentUser().routes.size
            }
        }

        @Test
        @DisplayName("Route preview")
        fun routePreview() {
            assertThrows<ServiceException> {
                mapService.getRoutePreview(emptyList())
            }
        }

        @Test
        @DisplayName("Entry preview")
        fun entryPreview() {
            assertThrows<Exception> {
                mapService.entryPreview(0, emptyList())
            }
        }

        @Test
        @DisplayName("Start entry")
        fun startEntry() {
            assertThrows<Exception> {
                mapService.startEntry(0, Point(0.0, 0.0, 0.0, DateTime.now(), -1))
            }
        }
    }
}