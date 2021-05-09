package me.kolotilov.letsagoservice

import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.persistance.entities.toUserEntity
import org.joda.time.DateTime
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import kotlin.test.assertEquals

class UserTests : BaseTests() {

    @Test
    @DisplayName("Current user")
    fun currentUser() {
        assertEquals("test@gmail.com", userService.getCurrentUser().username)
    }

    @Test
    @DisplayName("Get user by username")
    fun getByUsername() {
        val username = "newUser@gmail.com"
        val newUser = User(
            username = username,
            password = "test1234",
            confirmationUrl = ""
        )
        userRepository.save(newUser.toUserEntity())
        assertEquals(username, userService.get(username)?.username)
    }

    @Test
    @DisplayName("Edit user details")
    fun editDetails() {
        val newName = "Brad"
        val newBirthDate = DateTime.now().minusYears(20)
        val newHeight = 190
        val newWeight = 100
        val newUser = userService.edit(
            name = newName,
            birthDate = newBirthDate,
            height = newHeight,
            weight = newWeight,
            illnesses = null,
            symptoms = null,
            filter = null,
            updateFilter = false
        )
        assertEquals(newName, newUser.name)
        assertEquals(newBirthDate, newUser.birthDate)
        assertEquals(newHeight, newUser.height)
        assertEquals(newWeight, newUser.weight)
    }

    @Test
    @DisplayName("Change password")
    fun changePassword() {
        val newPassword = "test1234"
        val newUser = userService.changePassword(newPassword)

        assertEquals(newPassword, newUser.password)
    }
}