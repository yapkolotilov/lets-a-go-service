package me.kolotilov.letsagoservice.configuration

import me.kolotilov.letsagoservice.domain.models.Illness
import me.kolotilov.letsagoservice.domain.models.Symptom
import me.kolotilov.letsagoservice.domain.services.IllnessService
import me.kolotilov.letsagoservice.domain.services.SymptomService
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer(
    private val symptomService: SymptomService,
    private val illnessService: IllnessService
) {

    @PostConstruct
    fun initialize() {
        symptomService.clear()
        symptomService.createAll(
            "Тяжесть",
            "Боли в ногах",
            "Зуд",
            "Тяжело сгибать ноги",
            "Отёчность"
        )

        illnessService.clear()
        illnessService.createAll(
            Illness(
                "Варикоз", true, listOf(
                    Symptom("Тяжесть", true),
                    Symptom("Отёчность", true)
                )
            ),
            Illness(
                "Артроз", true, listOf(
                    Symptom("Боли в ногах", true)
                )
            ),
            Illness(
                "Артрит", true, listOf(
                    Symptom("Боли в ногах", true),
                    Symptom("Тяжело сгибать ноги", true)
                )
            )
        )
    }
}