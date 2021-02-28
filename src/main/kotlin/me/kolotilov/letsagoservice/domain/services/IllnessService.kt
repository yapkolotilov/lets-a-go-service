package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.domain.models.Illness
import me.kolotilov.letsagoservice.persistance.entities.toIllness
import me.kolotilov.letsagoservice.persistance.entities.toIllnessEntity
import me.kolotilov.letsagoservice.persistance.repositories.IllnessRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.stereotype.Service

/**
 * Сервис для работы с заболеваниями.
 */
interface IllnessService {

    /**
     * Возвращает или создаёт заболевание.
     *
     * @param names Список названий заболеваний.
     */
    fun getOrCreateAll(names: List<String>): List<Illness>

    /**
     * Возвращает все одобренные заболевания.
     *
     */
    fun getAllApproved(): List<Illness>

    /**
     * Очищает базу заболеваний.
     */
    fun clear()

    /**
     * Создаёт новые заболевания.
     *
     * @param illnesses Заболевания.
     */
    fun createAll(vararg illnesses: Illness): List<Illness>
}

@Service
private class IllnessServiceImpl(
    private val illnessRepository: IllnessRepository
) : IllnessService {

    override fun getOrCreateAll(names: List<String>): List<Illness> {
        return names.map { name ->
            val illness = illnessRepository.findByName(name).toNullable()?.toIllness()
            illness ?: illnessRepository.save(Illness(name, false, emptyList(), null).toIllnessEntity()).toIllness()
        }
    }

    override fun getAllApproved(): List<Illness> {
        return illnessRepository.findAllByApprovedTrue().map { it.toIllness() }
    }

    override fun clear() {
        illnessRepository.deleteAll()
    }

    override fun createAll(vararg illnesses: Illness): List<Illness> {
        return illnessRepository.saveAll(illnesses.map { it.toIllnessEntity() })
            .map { it.toIllness() }
    }
}