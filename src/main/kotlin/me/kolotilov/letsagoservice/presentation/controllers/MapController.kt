package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import me.kolotilov.letsagoservice.domain.services.MapService
import me.kolotilov.letsagoservice.domain.services.UserService
import me.kolotilov.letsagoservice.presentation.input.*
import me.kolotilov.letsagoservice.presentation.output.EntryDto
import me.kolotilov.letsagoservice.presentation.output.RouteDetailsDto
import me.kolotilov.letsagoservice.presentation.output.toEntryDto
import me.kolotilov.letsagoservice.presentation.output.toRouteDetailsDto
import org.springframework.web.bind.annotation.*

@Api("Карта.")
@RestController
@RequestMapping("/map")
class MapController(
    private val mapService: MapService,
    private val userService: UserService
) : BaseController("MAP") {

    @ApiOperation("Возвращает список всех маршрутов на карте.")
    @GetMapping("/routes")
    fun getAllRoutes(
        @ApiParam("Фильтр.")
        @RequestParam("filter") filter: Boolean
    ): List<RouteDetailsDto> {
        return mapService.getAllRoutes(filter)
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
    fun findRoutes(
        @ApiParam("Название маршрута.")
        @RequestParam("name")
        name: String?,
        @ApiParam("Фильтр.")
        @RequestBody
        filter: FilterDto?
    ): List<RouteDetailsDto> {
        return mapService.findRoutes(name, filter?.toFilter())
            .map { it.toRouteDetailsDto() }
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
    fun getAllEntries(): List<EntryDto> {
        return mapService.getAllEntries().map { it.toEntryDto() }
    }
}