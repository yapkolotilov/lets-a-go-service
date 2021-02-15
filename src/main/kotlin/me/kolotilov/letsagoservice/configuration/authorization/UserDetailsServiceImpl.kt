package me.kolotilov.letsagoservice.configuration.authorization

import me.kolotilov.letsagoservice.domain.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.core.annotation.Order
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
@Qualifier(UserDetailsServiceImpl.QUALIFIER)
class UserDetailsServiceImpl : UserDetailsService {

    companion object {
        const val QUALIFIER = "UserDetailsServiceImpl"
    }

    @Autowired
    private lateinit var userService: UserService

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userService.get(username) ?: throw UsernameNotFoundException("Пользователь $username не найден!")
        return User(user.username, user.password, user.enabled, true, true, true, emptyList())
    }
}