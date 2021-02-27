package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
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
) {

    @ApiOperation("Возвращает список всех маршрутов на карте.")
    @GetMapping("/routes")
    fun getAllRoutes(
        @ApiParam("Фильтр.")
        @RequestBody filter: FilterDto?
    ): List<RoutePreviewDto> {
        return mapService.getAllRoutes(filter?.toFilter())
            .map { it.toRoutePreviewDto(userService.getCurrentUser(), filter?.toFilter()) }
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
    fun findRoutes(
        @ApiParam("Название маршрута.")
        @RequestParam("name")
        name: String?,
        @ApiParam("Фильтр.")
        @RequestBody
        filter: FilterDto?
    ): List<RoutePreviewDto> {
        return mapService.findRoutes(name, filter?.toFilter())
            .map { it.toRoutePreviewDto(userService.getCurrentUser(), filter?.toFilter()) }
    }

    @ApiOperation("Создание маршрута.")
    @PostMapping("/routes")
    fun createRoute(
        @ApiParam("Параметры маршрута.")
        @RequestBody
        route: CreateRouteDto
    ): RouteDetailsDto {
        return mapService.createRoute(route.toRoute(userService.getCurrentUser())).toRouteDetailsDto()
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
        return mapService.editRoute(
            route.toRoute(oldRoute).copy(id = id)
        ).toRouteDetailsDto()
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

    @ApiOperation("Создание прохода.")
    @PostMapping("/entries")
    fun createEntry(
        @ApiParam("Параметры прохода.")
        @RequestBody
        createEntryDto: CreateEntryDto
    ): RouteDetailsDto {
        val route = mapService.getRoute(createEntryDto.routeId)
        return mapService.createEntry(route.id, createEntryDto.toEntry(route, userService.getCurrentUser()))
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
    fun getAllEntries(): List<EntryDto> {
        return mapService.getAllEntries().map { it.toEntryDto() }
    }
}