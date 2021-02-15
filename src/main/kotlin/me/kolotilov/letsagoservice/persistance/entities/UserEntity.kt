package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.User
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

/**
 * Пользователь.
 *
 * @param username Имя пользователя (e-mail).
 * @param password Пароль.
 * @param confirmationUrl URL подтверждения.
 * @param enabled Подтверждён ли аккаунт.
 */
@Entity
@Table(name = "user_entity")
data class UserEntity(
    @Id
    @Column(name = "username")
    val username: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "confirmation_url")
    val confirmationUrl: String,
    @Column(name = "enabled")
    val enabled: Boolean
)

fun UserEntity.toUser() = User(
    username = username,
    password = password,
    confirmationUrl = confirmationUrl,
    enabled = enabled
)

fun User.toUserEntity() = UserEntity(
    username = username,
    password = password,
    confirmationUrl = confirmationUrl,
    enabled = enabled
)