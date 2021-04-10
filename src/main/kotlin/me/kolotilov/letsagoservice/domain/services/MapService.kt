package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.configuration.ErrorCode
import me.kolotilov.letsagoservice.configuration.ServiceException
import me.kolotilov.letsagoservice.domain.models.*
import me.kolotilov.letsagoservice.persistance.entities.toEntry
import me.kolotilov.letsagoservice.persistance.entities.toRoute
import me.kolotilov.letsagoservice.persistance.entities.toRouteEntity
import me.kolotilov.letsagoservice.persistance.repositories.EntryRepository
import me.kolotilov.letsagoservice.persistance.repositories.RouteRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.stereotype.Service
import kotlin.math.pow
import kotlin.math.roundToInt

/**
 * Сервис для работы с картой.
 */
interface MapService {

    /**
     * Возвращает список всех маршрутов.
     */
    fun getAllRoutes(filter: Boolean): List<Route>

    /**
     * Возвращает маршрут по id.
     *
     * @param id ID маршрута.
     */
    fun getRoute(id: Int): Route

    /**
     * Выполняет поиск маршрутов.
     *
     * @param name Название маршрута.
     * @param filter Фильтр.
     */
    fun findRoutes(name: String?, filter: Filter?): List<Route>

    /**
     * Создаёт новый маршрут.
     *
     * @param routeId Маршрут.
     */
    fun createRoute(route: Route): Route

    /**
     * Редактирует маршрут.
     *
     * @param routeId Маршрут.
     */
    fun editRoute(route: Route): Route

    /**
     * Удаляет маршрут.
     *
     * @param routeId ID маршрута.
     */
    fun deleteRoute(routeId: Int)

    /**
     * Создаёт проход маршрута.
     *
     * @param routeId ID маршрута.
     * @param entry поход.
     */
    fun createEntry(routeId: Int, entry: Entry): Route

    /**
     * Удаляет проход.
     *
     * @param entryId ID прохода.
     */
    fun deleteEntry(entryId: Int): Route

    /**
     * Возвращает все походы по юзеру.
     */
    fun getAllEntries(): List<Entry>

    fun getEntry(id: Int): Entry

    fun clearRoutes()

    fun getRoutePreview(points: List<Point>): RoutePreview

    fun entryPreview(routeId: Int, points: List<Point>): EntryPreview
}

@Service
private class MapServiceImpl(
    private val routeRepository: RouteRepository,
    private val entryRepository: EntryRepository,
    private val userService: UserService
) : MapService {

    override fun getAllRoutes(filter: Boolean): List<Route> {
        val user = userService.getCurrentUser()
        return routeRepository.findAll()
            .map { it.toRoute() }
            .filter { it.isPublic || user.routes.contains(it) }
            .filter {
                if (filter)
                    userService.getCurrentUser().filter.matches(it)
                else
                    true
            }
    }

    override fun getRoute(id: Int): Route {
        return routeRepository.findById(id).toNullable()
            ?.toRoute()!!
    }

    override fun findRoutes(name: String?, filter: Filter?): List<Route> {
        return routeRepository.findAll()
            .map { it.toRoute() }
            .filter { name?.equals(it.name) ?: true }
            .filter { filter?.matches(it) ?: true }
    }

    override fun createRoute(route: Route): Route {
        val user = userService.getCurrentUser()
        val newUser = user.copy(routes = user.routes + route)
        userService.update(newUser)
        return routeRepository.save(route.toRouteEntity()).toRoute()
    }

    override fun editRoute(route: Route): Route {
        return routeRepository.save(route.toRouteEntity()).toRoute()
    }

    override fun deleteRoute(routeId: Int) {
        routeRepository.deleteById(routeId)
    }

    override fun createEntry(routeId: Int, entry: Entry): Route {
        var route = routeRepository.findById(routeId).toNullable()?.toRoute()!!
        route = route.copy(
            entries = route.entries + entry
        )
        val user = userService.getCurrentUser()
        val newUser = user.copy(entries = user.entries + entry)
        userService.update(user)
        return routeRepository.save(route.toRouteEntity()).toRoute()
    }

    override fun deleteEntry(entryId: Int): Route {
        val route = routeRepository.findByEntriesContaining(entryRepository.findById(entryId).get())
        entryRepository.deleteById(entryId)
        return route.toRoute()
    }

    override fun getAllEntries(): List<Entry> {
        return userService.getCurrentUser().entries
    }

    override fun clearRoutes() {
        routeRepository.deleteAll()
    }

    override fun getRoutePreview(points: List<Point>): RoutePreview {
        val distance = points.distance()
        if (points.isEmpty() || distance < 100)
            throw ServiceException(ErrorCode.ENTRY_TOO_SHORT)
        val speed = points.speed()
        val difficulty = when (distance / 1000) {
            in 0.0..0.1 -> 1
            in 0.1..2.0 -> 1
            in 2.0..4.0 -> 2
            in 4.0..6.0 -> 3
            in 6.0..8.0 -> 4
            else -> 5
        }
        val type = when (speed) {
            in 0.0..6.0 -> Route.Type.WALKING
            in 6.0..25.0 -> Route.Type.RUNNING
            in 25.00..100.0 -> Route.Type.CYCLING
            else -> throw ServiceException(ErrorCode.SPEED_TO_FAST)
        }
        return RoutePreview(
            distance = distance,
            duration = points.duration(),
            speed = speed,
            altitudeDelta = points.altitudeDelta(),
            type = type,
            difficulty = difficulty,
            kiloCaloriesBurnt = kiloCaloriesBurnt(userService.getCurrentUser(), type, points)
        )
    }

    override fun entryPreview(routeId: Int, points: List<Point>): EntryPreview {
        val route = getRoute(routeId)
        if (points.isEmpty())
            throw ServiceException(ErrorCode.ENTRY_TOO_SHORT)
        return EntryPreview(
            distance = points.distance(),
            duration = points.duration(),
            speed = points.speed(),
            kiloCaloriesBurnt = kiloCaloriesBurnt(userService.getCurrentUser(), route.type, points),
            altitudeDelta = points.altitudeDelta(),
            passed = (points.last() distance route.points.last()) < 100,
            routeId = routeId
        )
    }

    override fun getEntry(id: Int): Entry {
        return entryRepository.findById(id).toNullable()?.toEntry()!!
    }
}

fun kiloCaloriesBurnt(user: User, type: Route.Type?, points: List<Point>): Int? {
    if (user.height == null || user.weight == null) return null
    return when (type) {
        Route.Type.WALKING -> {
            (0.035 * user.weight +
                    (points.speed().pow(2) / user.height) * 0.029 * user.weight) * points.duration().standardMinutes
        }
        Route.Type.RUNNING -> {
            user.weight * points.distance() / 1000
        }
        Route.Type.CYCLING -> {
            0.014 * user.weight * points.duration().standardMinutes * (0.12 * 158 - 7)
        }
        else -> return null
    }.roundToInt()
}