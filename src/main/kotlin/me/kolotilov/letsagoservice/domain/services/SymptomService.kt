package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.domain.models.Symptom
import me.kolotilov.letsagoservice.persistance.entities.toSymptom
import me.kolotilov.letsagoservice.persistance.entities.toSymptomEntity
import me.kolotilov.letsagoservice.persistance.repositories.SymptomRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.stereotype.Service

/**
 * Сервис для работы с симптомами.
 */
interface SymptomService {

    /**
     * Возвращает или создаёт симптомы.
     *
     * @param names Описания симптомов.
     */
    fun getOrCreateAll(names: List<String>): List<Symptom>

    /**
     * Возвращает все зарегистрированные симптомы.
     */
    fun getAllApproved(): List<Symptom>

    /**
     * Создаёт симптомы.
     */
    fun createAll(vararg symptoms: String): List<Symptom>
}

@Service
private class SymptomServiceImpl(
    private val symptomRepository: SymptomRepository
) : SymptomService {

    override fun getOrCreateAll(names: List<String>): List<Symptom> {
        return names.map { name ->
            val symptom = symptomRepository.findByName(name).toNullable()?.toSymptom()
            symptom ?: symptomRepository.save(Symptom(name, false, null).toSymptomEntity()).toSymptom()
        }
    }

    override fun getAllApproved(): List<Symptom> {
        return symptomRepository.findAllByApprovedTrue().map { it.toSymptom() }
    }

    override fun createAll(vararg symptoms: String): List<Symptom> {
        return symptomRepository.saveAll(symptoms.map { Symptom(it, approved = true, null).toSymptomEntity() })
            .map { it.toSymptom() }
    }
}