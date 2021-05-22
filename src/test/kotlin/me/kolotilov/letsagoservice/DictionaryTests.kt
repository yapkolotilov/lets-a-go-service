package me.kolotilov.letsagoservice

import me.kolotilov.letsagoservice.domain.models.Illness
import me.kolotilov.letsagoservice.domain.models.Symptom
import me.kolotilov.letsagoservice.persistance.entities.toIllnessEntity
import me.kolotilov.letsagoservice.persistance.entities.toSymptomEntity
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue

class DictionaryTests : BaseTests() {

    @Nested
    @DisplayName("Illnesses")
    inner class Illnesses {

        @BeforeEach
        fun beforeEach() {
            val baseIllness = Illness(
                name = "base",
                approved = true,
                symptoms = emptyList(),
                filter = null
            )
            illnessRepository.saveAll(listOf(
                baseIllness.copy(name = "Варикоз"),
                baseIllness.copy(name = "Артрит"),
                baseIllness.copy(name = "Артроз")
            ).map { it.toIllnessEntity() })
        }

        @Test
        @DisplayName("Finding all illnesses")
        fun getAll() {
            assertTrue {
                symptomService.getAllApproved().isNotEmpty()
            }
        }

        @Test
        @DisplayName("Creating illnesses")
        fun getOrCreateAll() {
            assertTrue {
                symptomService.getOrCreateAll(listOf("bruh", "test")).size == 2
            }
        }

        @Test
        @DisplayName("Creating duplicate illnesses")
        fun getOrCreateDuplicate() {
            assertTrue {
                symptomService.getOrCreateAll(listOf("Варикоз", "Артрит")).size == 2
            }
        }
    }
    
    @Nested
    @DisplayName("Symptoms")
    inner class Symptoms {
        @BeforeEach
        fun beforeEach() {
            val baseSymptom = Symptom(
                name = "base",
                approved = true,
                filter = null
            )
            symptomRepository.saveAll(listOf(
                baseSymptom.copy(name = "Варикоз"),
                baseSymptom.copy(name = "Артрит"),
                baseSymptom.copy(name = "Артроз")
            ).map { it.toSymptomEntity() })
        }

        @Test
        @DisplayName("Finding all symptoms")
        fun getAll() {
            assertTrue {
                symptomService.getAllApproved().isNotEmpty()
            }
        }

        @Test
        @DisplayName("Creating illnesses")
        fun getOrCreateAll() {
            assertTrue {
                symptomService.getOrCreateAll(listOf("bruh", "test")).size == 2
            }
        }

        @Test
        @DisplayName("Creating duplicate illnesses")
        fun getOrCreateDuplicate() {
            assertTrue {
                symptomService.getOrCreateAll(listOf("Варикоз", "Артрит")).size == 2
            }
        }
    }
}