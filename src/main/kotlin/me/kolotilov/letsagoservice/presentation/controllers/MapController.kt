package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.services.MapService
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.presentation.input.*
import me.kolotilov.letsagoservice.presentation.output.*
import org.springframework.web.bind.annotation.*

@Api("Карта.")
@RestController
@RequestMapping("/map")
class MapController(
    private val mapService: MapService,
    private val userService: UserService
) : BaseController("MAP") {

    @ApiOperation("Возвращает список всех маршрутов на карте.")
    @PostMapping("/routes")
    fun getRoutes(
        @ApiParam("Фильтр.")
        @RequestParam("filter") filter: Boolean,
        @ApiParam("Левый верхний угол")
        @RequestBody
        coordinatesDto: CoordinatesDto
    ): List<RouteDetailsDto> {
        return mapService.getRoutes(filter)
            .map { it.toRouteDetailsDto() }
    }

    @ApiOperation("Возвращает маршрут по id.")
    @GetMapping("/routes/{id}")
    fun getRoute(
        @ApiParam("ID маршрута.")
        @PathVariable("id")
        id: Int
    ): RouteDetailsDto {
        return mapService.getRoute(id).toRouteDetailsDto()
    }

    @ApiOperation("Поиск маршрута.")
    @PostMapping("/routes/search")
    fun searchRoutes(
        @ApiParam("Запрос.")
        @RequestBody
        query: SearchRoutesDto
    ): List<RouteItemDto> {
        log.info("search routes: name: ${query.name}, filter: ${query.filter?.toFilter()}")
        return mapService.findRoutes(query.name, query.filter?.toFilter())
            .also { log.info("search routes: found routes: ${it.size}") }
            .map { it.toRouteItemDto(query.userLocation?.toPoint()) }
    }

    @ApiOperation("Превью маршрута.")
    @PostMapping("/routes/preview")
    fun routePreview(
        @ApiParam("Точки маршрута.")
        @RequestBody
        points: CreateRoutePreviewDto
    ): RoutePreviewDto {
        return mapService.getRoutePreview(points.points.map { it.toPoint() }).toRoutePreviewDto()
    }

    @ApiOperation("Создание маршрута.")
    @PostMapping("/routes")
    fun createRoute(
        @ApiParam("Параметры маршрута.")
        @RequestBody
        route: CreateRouteDto
    ): RouteDetailsDto {
        return mapService.createRoute(route.toRoute()).toRouteDetailsDto()
    }

    @ApiOperation("Редактирование маршрута.")
    @PostMapping("/routes/{id}")
    fun editRoute(
        @ApiParam("ID маршрута.")
        @PathVariable("id")
        id: Int,
        @ApiParam("Параметры маршрута.")
        @RequestBody
        route: EditRouteDto
    ): RouteDetailsDto {
        val oldRoute = mapService.getRoute(id)
        return mapService.editRoute(route.toRoute(oldRoute)).toRouteDetailsDto()
    }

    @ApiOperation("Удаление маршрута.")
    @DeleteMapping("/routes/{id}")
    fun deleteRoute(
        @ApiParam("ID маршрута.")
        @PathVariable("id")
        id: Int
    ) {
        mapService.deleteRoute(id)
    }

    @ApiOperation("Начать поход.")
    @PostMapping("/routes/{id}/startEntry")
    fun startEntry(
        @ApiParam("ID маршрута.")
        @PathVariable("id")
        id: Int,
        @RequestBody
        location: CreatePointDto
    ): StartEntryDto {
        return mapService.startEntry(id, location.toPoint()).toStartEntryDto()
    }

    @ApiOperation("Превью похода.")
    @PostMapping("/entries/preview")
    fun entryPreview(
        @RequestBody entry: CreateEntryPreviewDto
    ): EntryPreviewDto {
        return mapService.entryPreview(entry.routeId, entry.points.map { it.toPoint() }).toEntryPreviewDto()
    }

    @ApiOperation("Создание прохода.")
    @PostMapping("/routes/{id}/entries")
    fun createEntry(
        @ApiParam("ID маршрута.")
        @PathVariable("id")
        id: Int,
        @ApiParam("Параметры прохода.")
        @RequestBody
        createEntryDto: CreateEntryDto
    ): RouteDetailsDto {
        return mapService.createEntry(id, createEntryDto.toEntry())
            .toRouteDetailsDto()
    }

    @ApiOperation("Удаление прохода.")
    @DeleteMapping("/entries/{id}")
    fun deleteEntry(
        @ApiParam("ID прохода.")
        @PathVariable("id")
        id: Int
    ): RouteDetailsDto {
        return mapService.deleteEntry(id).toRouteDetailsDto()
    }

    @ApiOperation("Возвращает все проходы.")
    @GetMapping("/entries")
    fun getAllEntries(): List<EntryItemDto> {
        val routes = mapService.getRoutes(false)
        return mapService.getAllEntries().map { entry ->
            val route = routes.firstOrNull { it.entries.any { it.id == entry.id } }
            entry.toRouteEntryDto(route)
        }
    }

    @ApiOperation("Возвращает поход.")
    @GetMapping("/entries/{id}")
    fun getEntry(
        @ApiParam("ID похода.")
        @PathVariable("id")
        id: Int
    ): EntryDetailsDto {
        val route = mapService.getRoutes(false).first { route ->
            route.entries.any { it.id == id }
        }
        return mapService.getEntry(id).toEntryDetailsDto(route, userService.getCurrentUser())
    }

    @ApiOperation("Возвращает маршрут на карте")
    @GetMapping("/routes/{id}/map")
    fun getRouteOnMap(
        @ApiParam("ID маршрута.")
        @PathVariable("id")
        id: Int
    ): RouteLineDto {
        return mapService.getRoute(id).toRouteLineDto()
    }

    private fun Route.toRouteDetailsDto() = toRouteDetailsDto(userService.getCurrentUser())
}