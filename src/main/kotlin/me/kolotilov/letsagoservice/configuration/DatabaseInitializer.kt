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

/**
 * Наполняет тестовыми данными базу заболеваний.
 */
@Component
class DatabaseInitializer(
    private val symptomService: SymptomService,
    private val illnessService: IllnessService,
    private val entitiesService: EntitiesService
) {

    @PostConstruct
    fun initialize() {
        entitiesService.clear()

        // Инициализируем симптомы:
        symptomService.createAll(
            "Боли в ногах",
            "Тяжесть при движении",
            "Зуд при неподвижном состоянии",
            "Тяжело сгибать ноги",
            "Отёчность",
            "Боли в спине",
            "Усталость",
            "Головокружение",
            "Вздутие вен",
            "Скованность",
            "Боли при ходьбе",
            "Неприятные ощущения по ночам",
            "Судороги",
            "Одышка",
            "Боли в суставах",
            "Деформация суставов",
            "Чувство холода",
            "Боли в спине"
        )

        // Инициализируем заболевания:
        illnessService.createAll(
            Illness(
                name = "Варикоз",
                approved = true,
                symptoms = listOf(
                    Symptom("Тяжесть при движении", true, null),
                    Symptom("Отёчность", true, null),
                    Symptom("Вздутие вен", true, null),
                    Symptom("Боли при ходьбе", true, null),
                ),
                filter = Filter(
                    length = 2_000.0..5_000.0,
                    duration = Duration.standardMinutes(30)..Duration.standardHours(3),
                    typesAllowed = Route.Type.values().toList(),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 0
                )
            ),
            Illness(
                name = "Артроз",
                approved = true,
                symptoms = listOf(
                    Symptom(
                        "Боли в ногах", true, Filter(
                            enabled = true,
                            length = 1_000.0..3000.0,
                            duration = Duration.standardMinutes(30)..Duration.standardHours(3),
                            groundsAllowed = null,
                            typesAllowed = null,
                            id = 0
                        )
                    ),
                    Symptom("Скованность", true, null),
                    Symptom("Неприятные ощущения по ночам", true, null),
                ),
                Filter(
                    length = 1_000.0..3_000.0,
                    duration = Duration.standardMinutes(30)..Duration.standardHours(2),
                    typesAllowed = listOf(Route.Type.WALKING, Route.Type.CYCLING),
                    groundsAllowed = listOf(Route.Ground.ASPHALT),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Атеросклероз",
                approved = true,
                symptoms = listOf(
                    Symptom("Покраснения на ногах", true, null),
                    Symptom("Одышка", true, null)
                ),
                filter = Filter(
                    length = 1_000.0..4_000.0,
                    duration = Duration.standardMinutes(30)..Duration.standardHours(2),
                    typesAllowed = listOf(Route.Type.WALKING),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Артрит ног",
                approved = true,
                symptoms = listOf(
                    Symptom("Боли в суставах", true, null),
                    Symptom("Отёчность", true, null),
                    Symptom("Деформация суставов", true, null)
                ),
                filter = Filter(
                    length = 2_000.0..3_500.0,
                    duration = Duration.standardHours(1)..Duration.standardHours(3),
                    typesAllowed = listOf(Route.Type.WALKING, Route.Type.CYCLING),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Остехондроз",
                approved = true,
                symptoms = listOf(
                    Symptom("Боли в спине", true, null),
                    Symptom("Боли в ногах", true, null),
                    Symptom("Онемение", true, null)
                ),
                filter = Filter(
                    length = 1_000.0..4_000.0,
                    duration = Duration.standardHours(1)..Duration.standardHours(4),
                    typesAllowed = Route.Type.values().toList(),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Эндартериит",
                approved = true,
                symptoms = listOf(
                    Symptom("Тяжесть при движении", true, null),
                    Symptom("Отёчность", true, null),
                    Symptom("Боли при ходьбе", true, null),
                    Symptom("Судороги", true, null)
                ),
                filter = Filter(
                    length = 2_000.0..5_000.0,
                    duration = Duration.standardHours(2)..Duration.standardHours(5),
                    typesAllowed = Route.Type.values().toList(),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Тромбофлебит",
                approved = true,
                symptoms = listOf(
                    Symptom("Тяжесть при движении", true, null),
                    Symptom("Отёчность", true, null),
                    Symptom("Вздутие вен", true, null),
                    Symptom("Боли при ходьбе", true, null),
                    Symptom("Температура", true, null)
                ),
                filter = Filter(
                    length = 2_000.0..5_000.0,
                    duration = Duration.standardMinutes(45)..Duration.standardHours(3),
                    typesAllowed = Route.Type.values().toList(),
                    groundsAllowed = Route.Ground.values().toList(),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Последствия перелома",
                approved = true,
                symptoms = listOf(
                    Symptom("Тяжесть при движении", true, null),
                    Symptom("Боли при ходьбе", true, null),
                    Symptom("Онемение", true, null),
                ),
                filter = Filter(
                    length = 1000.0..1_500.0,
                    duration = Duration.standardMinutes(5)..Duration.standardHours(1),
                    typesAllowed = listOf(Route.Type.WALKING),
                    groundsAllowed = listOf(Route.Ground.ASPHALT),
                    enabled = true,
                    id = 0
                )
            ),

            Illness(
                name = "Последствия вывиха",
                approved = true,
                symptoms = listOf(
                    Symptom("Тяжесть при движении", true, null),
                    Symptom("Боли при ходьбе", true, null),
                    Symptom("Онемение", true, null),
                ),
                filter = Filter(
                    length = 1_000.0..2000.0,
                    duration = Duration.standardMinutes(20)..Duration.standardHours(1),
                    typesAllowed = listOf(Route.Type.WALKING),
                    groundsAllowed = listOf(Route.Ground.ASPHALT),
                    enabled = true,
                    id = 0
                )
            ),
        )
    }
}