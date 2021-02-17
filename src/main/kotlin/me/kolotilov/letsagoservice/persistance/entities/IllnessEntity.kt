package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Illness
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
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
    @Cascade(CascadeType.ALL)
    @OneToMany
    val symptoms: List<SymptomEntity>
)

fun IllnessEntity.toIllness() = Illness(
    name = name,
    approved = approved,
    symptoms = symptoms.map { it.toSymptom() }
)

fun Illness.toIllnessEntity() = IllnessEntity(
    name = name,
    approved = approved,
    symptoms = symptoms.map { it.toSymptomEntity() }
)
