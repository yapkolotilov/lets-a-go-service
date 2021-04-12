package me.kolotilov.letsagoservice.presentation.controllers

import me.kolotilov.letsagoservice.configuration.LetsLogger


abstract class BaseController(
    private val tag: String
) {

    protected val log = LetsLogger(tag)
}