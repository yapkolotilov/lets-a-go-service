package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Illness
import javax.persistence.*

@Table(name = "illness")
@Entity
data class IllnessEntity(
    @Column(name = "name")
    @Id
    val name: String,
    @Column(name = "approved")
    val approved: Boolean,
    @JoinColumn(name = "illness_name")
    @OneToMany(cascade = [CascadeType.ALL])
    val symptoms: List<SymptomEntity>,
    @OneToOne(cascade = [CascadeType.ALL])
    @JoinColumn(name = "filter_id")
    val filter: FilterEntity?
)

fun IllnessEntity.toIllness() = Illness(
    name = name,
    approved = approved,
    symptoms = symptoms.map { it.toSymptom() },
    filter = filter?.toFilter()
)

fun Illness.toIllnessEntity() = IllnessEntity(
    name = name,
    approved = approved,
    symptoms = symptoms.map { it.toSymptomEntity() },
    filter = filter?.toFilterEntity()
)
