package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Symptom
import javax.persistence.*

@Entity
@Table(name = "symptom")
data class SymptomEntity(
    @Column(name = "name")
    @Id
    val name: String,
    @Column(name = "approved")
    val approved: Boolean,
    @OneToOne(cascade = [javax.persistence.CascadeType.ALL])
    @JoinColumn(name = "filter_id")
    val filter: FilterEntity?
)

fun SymptomEntity.toSymptom() = Symptom(
    name = name,
    approved = approved,
    filter = filter?.toFilter()
)

fun Symptom.toSymptomEntity() = SymptomEntity(
    name = name,
    approved = approved,
    filter = filter?.toFilterEntity()
)
