package me.kolotilov.letsagoservice.persistance.repositories

import me.kolotilov.letsagoservice.persistance.entities.EntryEntity
import me.kolotilov.letsagoservice.persistance.entities.RouteEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface RouteRepository : JpaRepository<RouteEntity, Int> {

    fun findByEntriesContaining(entry: EntryEntity): RouteEntity
}