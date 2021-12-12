package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.configuration.ErrorCode
import me.kolotilov.letsagoservice.configuration.LetsLogger
import me.kolotilov.letsagoservice.configuration.ServiceException
import me.kolotilov.letsagoservice.domain.models.*
import me.kolotilov.letsagoservice.persistance.entities.toEntry
import me.kolotilov.letsagoservice.persistance.entities.toEntryEntity
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
    fun getRoutes(filter: Boolean): List<Route>

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

    /**
     * Получение похода по ID.
     *
     * @param ID ID похода.
     */
    fun getEntry(id: Int): Entry

    /**
     * Очищает все маршруты.
     */
    fun clearRoutes()

    fun clearUsers()

    /**
     * Предпросмотр маршрута.
     *
     * @param points Пройденные точки.
     */
    fun getRoutePreview(points: List<Point>): RoutePreview

    /**
     * Предпросмотр похода.
     *
     * @param points Пройденные точки.
     * @param routeId ID маршрута.
     */
    fun entryPreview(routeId: Int, points: List<Point>): EntryPreview

    /**
     * Начало записи маршрута.
     *
     * @param id ID маршрута.
     * @param location Текущая локация пользователя.
     */
    fun startEntry(id: Int, location: Point): Route
}

@Service
private class MapServiceImpl(
    private val routeRepository: RouteRepository,
    private val entryRepository: EntryRepository,
    private val userService: UserService
) : MapService {

    companion object {

        private const val MIN_DISTANCE_TO_ROUTE = 50.0
    }

    override fun getRoutes(filter: Boolean): List<Route> {
        return routeRepository.findAll()
            .map { it.toRoute() }
            .filterAvailable()
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
            .filterAvailable()
            .filter { route ->
                name?.let { (route.name ?: "Маршрут").contains(name, true) } ?: true
            }
            .filter { filter?.matches(it) ?: true }
    }

    override fun createRoute(route: Route): Route {
        val newRoute = routeRepository.save(route.toRouteEntity()).toRoute()
        val user = userService.getCurrentUser()
        val newUser = user.copy(routes = user.routes + newRoute, entries = user.entries + newRoute.entries.first())
        userService.update(newUser)
        return newRoute
    }

    override fun editRoute(route: Route): Route {
        return routeRepository.save(route.toRouteEntity()).toRoute()
    }

    override fun deleteRoute(routeId: Int) {
        routeRepository.deleteById(routeId)
    }

    override fun createEntry(routeId: Int, entry: Entry): Route {
        var route = routeRepository.findById(routeId).toNullable()?.toRoute()!!
        val newEntry = entryRepository.save(entry.toEntryEntity()).toEntry()
        route = route.copy(
            entries = route.entries + newEntry
        )
        val user = userService.getCurrentUser()
        val newUser = user.copy(entries = user.entries + newEntry)
        userService.update(newUser)
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

    override fun clearUsers() {
        userService.deleteAll()
    }

    override fun getRoutePreview(points: List<Point>): RoutePreview {
        val distance = points.distance()
        checkSpeed(points)
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
            in 0.0..10.0 -> Route.Type.WALKING
            in 10.0..20.0 -> Route.Type.RUNNING
            in 20.00..50.0 -> Route.Type.CYCLING
            else -> throw ServiceException(ErrorCode.SPEED_TOO_FAST)
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
        checkSpeed(points)
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

    override fun startEntry(id: Int, location: Point): Route {
        val route = getRoute(id)
        val startPoint = route.points.first()
        val endPoint = route.points.last()
        if (location distance startPoint > MIN_DISTANCE_TO_ROUTE && location distance endPoint > MIN_DISTANCE_TO_ROUTE)
            throw ServiceException(ErrorCode.TOO_FAR_FROM_ROUTE)
        return route
    }

    private fun checkSpeed(points: List<Point>) {
        val maxSpeed = 50
        if (points.zipWithNext { a, b -> listOf(a, b).speed() }.any { it > maxSpeed })
            throw ServiceException(ErrorCode.SPEED_TOO_FAST)
    }

    private fun List<Route>.filterAvailable(): List<Route> {
        val user = userService.getCurrentUser()
        return filter { it.isPublic || user.routes.contains(it) }
    }

    private fun Route.takeIfAvailable(): Route? {
        val user = userService.getCurrentUser()
        return takeIf { it.isPublic || user.routes.contains(it) }
    }
}

/**
 * Подсчёт сожжёных килокалорий.
 *
 * @param user Пользователь.
 * @param type Тип маршрута.
 * @param points Точки маршрута.
 */
fun kiloCaloriesBurnt(user: User, type: Route.Type?, points: List<Point>): Int? {
    if (user.height == null || user.weight == null) return null
    bodyMassIndex(user)
    return when (type) {
        Route.Type.WALKING -> {
            (0.035 * user.weight +
                    (points.speed()
                        .pow(2) / user.height) * 0.029 * user.weight) * (points.duration().standardSeconds.toDouble() / 60)
        }
        Route.Type.RUNNING -> {
            user.weight * points.distance() / 1000
        }
        Route.Type.CYCLING -> {
            0.014 * user.weight * (points.duration().standardSeconds.toDouble() / 60) * (0.12 * 158 - 7)
        }
        else -> return null
    }.roundToInt()
}

/**
 * Вычисляет индекс массы тела пользователя.
 *
 * @param user Пользователь.
 */
fun bodyMassIndex(user: User): Int? {
    val weight = user.weight ?: return null
    val height = user.height ?: return null
    return weight / (height * height)
}