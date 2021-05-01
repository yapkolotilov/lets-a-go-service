package me.kolotilov.letsagoservice.domain.services

import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Illness
import me.kolotilov.letsagoservice.domain.models.Symptom
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.persistance.entities.toUser
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import me.kolotilov.letsagoservice.persistance.repositories.UserRepository
import me.kolotilov.letsagoservice.utils.castTo
import me.kolotilov.letsagoservice.utils.toNullable
import org.joda.time.DateTime
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Service

/**
 * Сервис для работы с пользователями.
 */
interface UserService {

    /**
     * Возвращает текущего пользователя.
     */
    fun getCurrentUser(): User

    /**
     * Возвращает пользователя по его юзернейму.
     *
     * @param username Логин пользователя.
     */
    fun get(username: String): User?

    /**
     * Редактирует данные пользователя.
     *
     * @param name ФИО.
     * @param age: Возраст.
     * @param height Рост.
     * @param weight Вес.
     * @param illnesses Заболевания.
     * @param symptoms Симптомы.
     */
    fun edit(
        name: String?,
        birthDate: DateTime?,
        height: Int?,
        weight: Int?,
        illnesses: List<Illness>?,
        symptoms: List<Symptom>?,
        filter: Filter?,
        updateFilter: Boolean
    ): User

    /**
     * Меняет пароль.
     *
     * @param password Новый пароль.
     */
    fun changePassword(password: String): User

    /**
     * Обновляет информацию о пользователе.
     *
     * @param user Пользователь.
     */
    fun update(user: User): User
}

@Service
private class UserServiceImpl(
    private val userRepository: UserRepository
) : UserService {

    override fun getCurrentUser(): User {
        val username = SecurityContextHolder.getContext().authentication.principal
            .castTo<org.springframework.security.core.userdetails.User>().username
        return userRepository.findByUsername(username).toNullable()?.toUser()!!
    }

    override fun get(username: String): User? {
        return userRepository.findByUsername(username).toNullable()?.toUser()
    }

    override fun edit(
        name: String?,
        birthDate: DateTime?,
        height: Int?,
        weight: Int?,
        illnesses: List<Illness>?,
        symptoms: List<Symptom>?,
        filter: Filter?,
        updateFilter: Boolean
    ): User {
        val user = getCurrentUser()
        var newUser = user.copy(
            name = name ?: user.name,
            birthDate = birthDate ?: user.birthDate,
            height = height ?: user.height,
            weight = weight ?: user.weight,
            illnesses = illnesses ?: user.illnesses,
            symptoms = symptoms ?: user.symptoms,
            filter = filter ?: user.filter
        )
        if (updateFilter) {
            val filters = (newUser.illnesses.map { it.filter } +
                    newUser.illnesses.map { it.symptoms }.flatten().map { it.filter } +
                    newUser.symptoms.map { it.filter })
                .filterNotNull()
            newUser = newUser.copy(filter = Filter.compose(*filters.toTypedArray()))
        }

        return userRepository.save(newUser.toUserEntity()).toUser()
    }

    override fun changePassword(password: String): User {
        val user = getCurrentUser()
        val newUser = user.copy(password = password)
        return userRepository.save(newUser.toUserEntity()).toUser()
    }

    override fun update(user: User): User {
        return userRepository.save(user.toUserEntity()).toUser()
    }
}