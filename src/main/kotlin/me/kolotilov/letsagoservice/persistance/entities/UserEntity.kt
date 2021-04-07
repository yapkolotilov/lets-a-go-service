package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.User
import me.kolotilov.letsagoservice.utils.toDateTime
import java.util.*
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
    val name: String?,
    @Column(name = "birth_date")
    val birthDate: Date?,
    @Column(name = "height")
    val height: Int?,
    @Column(name = "weight")
    val weight: Int?,
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = [CascadeType.ALL])
    val illnesses: List<IllnessEntity>,
    @JoinColumn(name = "user_id")
    @OneToMany(cascade = [CascadeType.ALL])
    val symptoms: List<SymptomEntity>,
    @OneToOne(cascade = [CascadeType.ALL])
    val filter: FilterEntity,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_username")
    val routes: List<RouteEntity>,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "user_username")
    val entries: List<EntryEntity>
)

fun UserEntity.toUser(): User = User(
    username = username,
    password = password,
    confirmationUrl = confirmationUrl,
    name = name,
    birthDate = birthDate?.toDateTime(),
    height = height,
    weight = weight,
    illnesses = illnesses.map { it.toIllness() },
    symptoms = symptoms.map { it.toSymptom() },
    filter = filter.toFilter(),
    routes = routes.map { it.toRoute() },
    entries = entries.map { it.toEntry() },
)

fun User.toUserEntity(): UserEntity = UserEntity(
    username = username,
    password = password,
    confirmationUrl = confirmationUrl,
    name = name,
    birthDate = birthDate?.toDate(),
    height = height,
    weight = weight,
    illnesses = illnesses.map { it.toIllnessEntity() },
    symptoms = symptoms.map { it.toSymptomEntity() },
    filter = filter.toFilterEntity(),
    routes = routes.map { it.toRouteEntity() },
    entries = entries.map { it.toEntryEntity() },
)