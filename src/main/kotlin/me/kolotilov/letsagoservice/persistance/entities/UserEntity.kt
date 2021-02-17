package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Illness
import me.kolotilov.letsagoservice.domain.models.Symptom
import me.kolotilov.letsagoservice.domain.models.User
import org.hibernate.annotations.Cascade
import org.hibernate.annotations.CascadeType
import javax.persistence.*

@Table(name = "user_entity")
@Entity
data class UserEntity(
    @Column(name = "username")
    @Id
    val username: String,
    @Column(name = "password")
    val password: String,
    @Column(name = "confirmation_url")
    val confirmationUrl: String,
    @Column(name = "name")
    val name: String,
    @Column(name = "age")
    val age: Int = -1,
    @Column(name = "height")
    val height: Int = -1,
    @Column(name = "weight")
    val weight: Int = -1,
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    @OneToMany
    val illnesses: List<IllnessEntity>,
    @JoinColumn(name = "user_id")
    @Cascade(CascadeType.ALL)
    @OneToMany
    val symptoms: List<SymptomEntity>
)

fun UserEntity.toUser() = User(
    username = username,
    password = password,
    confirmationUrl = confirmationUrl,
    name = name,
    age = age,
    height = height,
    weight = weight,
    illnesses = illnesses.map { it.toIllness() },
    symptoms = symptoms.map { it.toSymptom() }
)

fun User.toUserEntity() = UserEntity(
    username = username,
    password = password,
    confirmationUrl = confirmationUrl,
    name = name,
    age = age,
    height = height,
    weight = weight,
    illnesses = illnesses.map { it.toIllnessEntity() },
    symptoms = symptoms.map { it.toSymptomEntity() }
)