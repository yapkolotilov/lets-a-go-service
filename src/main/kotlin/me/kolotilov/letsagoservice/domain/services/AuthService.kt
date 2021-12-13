package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.configuration.ErrorCode
import me.kolotilov.letsagoservice.configuration.ServiceException
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.persistance.entities.toUser
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import me.kolotilov.letsagoservice.persistance.repositories.UserRepository
import me.kolotilov.letsagoservice.utils.toNullable
import org.springframework.http.HttpStatus
import org.springframework.mail.javamail.JavaMailSenderImpl
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service
import kotlin.streams.asSequence

/**
 * Сервис авторизации.
 */
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
            throw ServiceException(
                code = ErrorCode.USER_ALREADY_EXITS,
                status = HttpStatus.BAD_REQUEST,
                message = "Пользователь $username уже зарегистрирован"
            )
        if (password.length < 8 || password.all { it.isDigit() } || password.all { it.isLetter() })
            throw ServiceException(
                code = ErrorCode.INVALID_PASSWORD,
                status = HttpStatus.BAD_REQUEST,
                message = "Пароль должен быть не короче 8 символов"
            )
        val url = generateUrl(username)
        val user = User(
            username = username,
            password = password,
            confirmationUrl = ""
        )
        try {
            sendEmail(username, url)
        } catch (e: Exception) {
            throw ServiceException(
                code = ErrorCode.INVALID_USERNAME,
                message = "Неправильный e-mail: такой почты не существует!",
                stackTrace = e.stackTraceToString()
            )
        }
        userRepository.save(user.toUserEntity())
    }

    override fun generateUrl(username: String): String {
        val source = "ABCDEFGHIJKLMNOPQRSTUVWXYZ".toLowerCase()
        return java.util.Random().ints(10, 0, source.length)
            .asSequence()
            .map(source::get)
            .joinToString("")
    }

    override fun confirmEmail(url: String): Boolean {
        println(userRepository.findAll().map { it.confirmationUrl })
        val user = userRepository.findByConfirmationUrl(url).toNullable() ?: return false
        userRepository.save(user.copy(confirmationUrl = ""))
        return true
    }

    private fun get(username: String): User? {
        return userRepository.findByUsername(username).toNullable()?.toUser()
    }

    private fun sendEmail(email: String, url: String) = runCatching {
        val mailSender = JavaMailSenderImpl().apply {
            host = "smtp.gmail.com"
            port = 465
            username = "letsagoservice@gmail.com"
            password = "Q!12345678"
            protocol = "smtp"
            val  props = javaMailProperties;
            props["mail.transport.protocol"] = "smtp";
            props["mail.smtp.auth"] = "true";
            props["mail.smtp.starttls.enable"] = "true";
            props["mail.debug"] = "true"
        }
        val mimeMessage = mailSender.createMimeMessage()
        MimeMessageHelper(mimeMessage, "utf-8").apply {
            setFrom("letsagoservice@gmail.com")
            setTo(email)
            setSubject("Подтверждение e-mail")
            val baseUrl = "https://lets-a-go.herokuapp.com"
            setText("<h4>Приветствуем вас!</h4>\n" +
                    "Перейдите по ссылке для подтверждения e-mail: <a>$baseUrl/auth/confirm_email/$url</a>", true)
        }
        mailSender.send(mimeMessage)
    }.run { Unit }
}