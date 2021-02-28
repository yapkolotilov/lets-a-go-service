package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.persistance.entities.toUser
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import me.kolotilov.letsagoservice.persistance.repositories.UserRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.stereotype.Service
import kotlin.random.Random

interface AuthService {

    /**
     * Регистрирует пользователя.
     *
     * @param username Логин.
     * @param password Пароль.
     */
    fun register(username: String, password: String)

    /**
     * Генерирует URL.
     *
     * @param username Имя пользователя.
     */
    fun generateUrl(username: String): String

    /**
     * Подтверждает e-mail.
     *
     * @param url URL.
     */
    fun confirmEmail(url: String): Boolean
}

@Service
class AuthServiceImpl(
    private val userRepository: UserRepository
)  : AuthService {

    private companion object {
        const val STRING_LENGTH = 15
    }

    override fun register(username: String, password: String) {
        if (get(username) != null)
            throw IllegalStateException("Пользователь $username уже зарегистрирован!")
        val url = generateUrl(username)

        val user = User(
            username = username,
            password = password,
            confirmationUrl = "" // TODO
        )
        userRepository.save(user.toUserEntity())
    }

    override fun generateUrl(username: String): String {
        val chars = Array(STRING_LENGTH) { Random.nextInt('A'.toInt(), 'z'.toInt()).toChar() }.toCharArray()
        return String(chars)
    }

    override fun confirmEmail(url: String): Boolean {
        val user = userRepository.findByConfirmationUrl(url).toNullable() ?: return false
        userRepository.save(user.copy(confirmationUrl = ""))
        return true
    }

    private fun get(username: String): User? {
        return userRepository.findByUsername(username).toNullable()?.toUser()
    }
}