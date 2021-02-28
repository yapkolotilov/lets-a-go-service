package me.kolotilov.letsagoservice.persistance.repositories

import me.kolotilov.letsagoservice.persistance.entities.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository : JpaRepository<UserEntity, String> {

    fun findByUsername(username: String): Optional<UserEntity>

    fun findByConfirmationUrl(url: String): Optional<UserEntity>
}