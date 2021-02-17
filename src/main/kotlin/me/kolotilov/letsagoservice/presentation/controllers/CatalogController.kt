package me.kolotilov.letsagoservice.presentation.controllers

import io.swagger.annotations.ApiOperation
import me.kolotilov.letsagoservice.domain.services.IllnessService
import me.kolotilov.letsagoservice.domain.services.SymptomService
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/catalog")
class CatalogController(
    private val illnessService: IllnessService,
    private val symptomService: SymptomService
) {

    @ApiOperation("Возвращает список всех доступных заболеваний.")
    @GetMapping("/illnesses")
    fun getIllnesses(): List<String> {
        return illnessService.getAllApproved().map { it.name }
    }

    @ApiOperation("Возвращает список всех доступных симптомов.")
    @GetMapping("/symptoms")
    fun getSymptoms(): List<String> {
        return symptomService.getAllApproved().map { it.name }
    }
}