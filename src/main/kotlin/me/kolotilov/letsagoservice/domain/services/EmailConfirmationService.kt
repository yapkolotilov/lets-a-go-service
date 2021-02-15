package me.kolotilov.letsagoservice.domain.services

import org.springframework.stereotype.Service
import kotlin.random.Random

interface EmailConfirmationService {

    fun generateUrl(username: String): String
}

@Service
private class EmailConfirmationServiceImpl : EmailConfirmationService {

    private companion object {
        const val STRING_LENGTH = 15
    }

    override fun generateUrl(username: String): String {
        val chars = Array(STRING_LENGTH) { Random.nextInt('A'.toInt(), 'z'.toInt()).toChar() }.toCharArray()
        return String(chars)
    }
}