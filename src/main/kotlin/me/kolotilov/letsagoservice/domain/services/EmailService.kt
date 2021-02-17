package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.persistance.repositories.UserRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.stereotype.Service
import kotlin.random.Random

/**
 * Сервис для работы с e-mail.
 */
interface EmailService {

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
private class EmailServiceImpl(
    private val userRepository: UserRepository
) : EmailService {

    private companion object {
        const val STRING_LENGTH = 15
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
}