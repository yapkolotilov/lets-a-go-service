package me.kolotilov.letsagoservice.presentation.controllers

import mu.KotlinLogging

abstract class BaseController(
    private val tag: String
) {

    protected val logger = KotlinLogging.logger(tag)
}