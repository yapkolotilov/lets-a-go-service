package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.persistance.entities.toRoute
import me.kolotilov.letsagoservice.persistance.entities.toRouteEntity
import me.kolotilov.letsagoservice.persistance.repositories.EntryRepository
import me.kolotilov.letsagoservice.persistance.repositories.RouteRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.stereotype.Service

/**
 * Сервис для работы с картой.
 */
interface MapService {

    /**
     * Возвращает список всех маршрутов.
     */
    fun getAllRoutes(filter: Filter?): List<Route>

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
}

@Service
private class MapServiceImpl(
    private val routeRepository: RouteRepository,
    private val entryRepository: EntryRepository,
    private val userService: UserService
) : MapService {

    override fun getAllRoutes(filter: Filter?): List<Route> {
        return routeRepository.findAll()
            .map { it.toRoute() }
            .filter { filter?.matches(it) ?: true }
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
}