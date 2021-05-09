package me.kolotilov.letsagoservice

import me.kolotilov.letsagoservice.configuration.ServiceException
import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import org.junit.jupiter.api.*

@DisplayName("Authorization")
class AuthTests : BaseTests() {

    @Nested
    @DisplayName("Register")
    inner class Registration {

        @Test
        @DisplayName("Register")
        fun register() {
            assertDoesNotThrow {
                authService.register("yaroslavattacher@gmail.com", "test1234")
            }
        }

        @Test
        @DisplayName("Invalid e-mail")
        fun invalidEmail() {
            assertThrows<ServiceException> {
                authService.register("", "test1234")
            }
        }

        @Test
        @DisplayName("Invalid password")
        fun invalidPassword() {
            assertThrows<ServiceException> {
                authService.register("yaroslavattacher@gmail.com", "123")
            }
        }
    }

    @Nested
    @DisplayName("Email confirmation")
    inner class EmailConfirmation {

        private val confirmationUrl = "q1234"

        @BeforeEach
        fun initUser() {
            userRepository.save(
                User(
                    username = "yapkolotilov@gmail.com",
                    password = "test1234",
                    confirmationUrl = confirmationUrl
                ).toUserEntity()
            )
        }

        @Test
        @DisplayName("Confirm email")
        fun confirmEmail() {
            assert(authService.confirmEmail(confirmationUrl))
        }

        @Test
        @DisplayName("Invalid url")
        fun invalidUrl() {
            assert(authService.confirmEmail(""))
        }
    }
}
