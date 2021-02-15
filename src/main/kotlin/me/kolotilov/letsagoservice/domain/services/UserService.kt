package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.persistance.entities.toUser
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import me.kolotilov.letsagoservice.persistance.repositories.UserRepository
import me.kolotilov.letsagoservice.utils.log
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

interface UserService {

    fun get(username: String): User?

    fun register(username: String, password: String)

    fun confirmEmail(url: String): Boolean
}

@Service
private class UserServiceImpl : UserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Autowired
    private lateinit var emailConfirmationService: EmailConfirmationService

    override fun get(username: String): User? {
        return userRepository.findByUsername(username).toNullable()?.toUser()
    }

    override fun register(username: String, password: String) {
        if (get(username) != null)
            throw IllegalStateException("Пользователь $username уже зарегистрирован!")
        val url = emailConfirmationService.generateUrl(username)
        log("BRUH", url)

        val user = User(
            username = username,
            password = password,
            confirmationUrl = url,
            enabled = false
        )
        userRepository.save(user.toUserEntity())
    }

    override fun confirmEmail(url: String): Boolean {
        val user = userRepository.findByConfirmationUrl(url).toNullable() ?: return false
        userRepository.save(user.copy(enabled = true))
        return true
    }
}