package me.kolotilov.letsagoservice.persistance.repositories

import me.kolotilov.letsagoservice.persistance.entities.SymptomEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface SymptomRepository : JpaRepository<SymptomEntity, String> {

    fun findByName(name: String): Optional<SymptomEntity>

    fun findAllByApprovedTrue(): List<SymptomEntity>
}