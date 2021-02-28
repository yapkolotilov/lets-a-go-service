package me.kolotilov.letsagoservice.persistance.repositories

import me.kolotilov.letsagoservice.persistance.entities.PointEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PointRepository : JpaRepository<PointEntity, Int>