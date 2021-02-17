package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Symptom
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "symptom")
data class SymptomEntity(
    @Column(name = "name")
    @Id
    val name: String,
    @Column(name = "approved")
    val approved: Boolean
)

fun SymptomEntity.toSymptom() = Symptom(
    name = name,
    approved = approved
)

fun Symptom.toSymptomEntity() = SymptomEntity(
    name = name,
    approved = approved
)
