package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.persistance.repositories.*
import org.springframework.stereotype.Service

/**
 * Управление всеми сущностями в проекте.
 */
interface EntitiesService {

    /**
     * Очищает БД.
     */
    fun clear()
}

@Service
class EntitiesServiceImpl(
    private val entryRepository: EntryRepository,
    private val filterRepository: FilterRepository,
    private val illnessRepository: IllnessRepository,
    private val pointRepository: PointRepository,
    private val routeRepository: RouteRepository,
    private val symptomRepository: SymptomRepository,
    private val userRepository: UserRepository
) : EntitiesService {

    override fun clear() {
    }
}