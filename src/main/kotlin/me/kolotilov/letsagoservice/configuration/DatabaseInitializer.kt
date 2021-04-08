package me.kolotilov.letsagoservice.configuration

import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Illness
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.domain.models.Symptom
import me.kolotilov.letsagoservice.domain.services.EntitiesService
import me.kolotilov.letsagoservice.domain.services.IllnessService
import me.kolotilov.letsagoservice.domain.services.SymptomService
import org.joda.time.Duration
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class DatabaseInitializer(
    private val symptomService: SymptomService,
    private val illnessService: IllnessService,
    private val entitiesService: EntitiesService
) {

    @PostConstruct
    fun initialize() {
        entitiesService.clear()

        symptomService.createAll(
            "Тяжесть",
            "Боли в ногах",
            "Зуд",
            "Тяжело сгибать ноги",
            "Отёчность"
        )

        illnessService.createAll(
            Illness(
                "Варикоз", true, listOf(
                    Symptom("Тяжесть", true, null),
                    Symptom("Отёчность", true, null)
                ),
                Filter(
                    length = 1.0..10.0,
                    duration = null,
                    typesAllowed = listOf(Route.Type.WALKING),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 1
                )
            ),
            Illness(
                "Артроз", true, listOf(
                    Symptom("Боли в ногах", true, null)
                ),
                Filter(
                    length = 0.0..5.0,
                    duration = Duration.standardHours(1)..Duration.standardHours(5),
                    typesAllowed = listOf(Route.Type.WALKING),
                    groundsAllowed = listOf(Route.Ground.ASPHALT),
                    enabled = true,
                    id = 2
                )
            ),
            Illness(
                "Артрит", true, listOf(
                    Symptom("Боли в ногах", true, null),
                    Symptom("Тяжело сгибать ноги", true, null)
                ),
                null
            )
        )
    }
}