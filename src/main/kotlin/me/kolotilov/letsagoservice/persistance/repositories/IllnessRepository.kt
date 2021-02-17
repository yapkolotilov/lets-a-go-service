package me.kolotilov.letsagoservice.persistance.repositories

import me.kolotilov.letsagoservice.persistance.entities.IllnessEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface IllnessRepository : JpaRepository<IllnessEntity, String> {

    fun findByName(name: String): Optional<IllnessEntity>

    fun findAllByApprovedTrue(): List<IllnessEntity>
}