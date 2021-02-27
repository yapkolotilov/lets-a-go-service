package me.kolotilov.letsagoservice.persistance.repositories

import me.kolotilov.letsagoservice.persistance.entities.EntryEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface EntryRepository : JpaRepository<EntryEntity, Int> {

    fun findAllByUserUsername(userUsername: String): List<EntryEntity>

    fun findAllByUserUsernameAndRouteId(userUsername: String, routeId: Int): List<EntryEntity>
}