package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.utils.toDate
import me.kolotilov.letsagoservice.utils.toDuration
import java.util.*
import javax.persistence.*

@Entity(name = "filter")
data class FilterEntity(
    @Column(name = "max_length")
    val maxLength: Double?,
    @Column(name = "max_duration")
    val maxDuration: Date?,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "filter_id")
    val typesAllowed: List<RouteTypeEntity>?,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "filter_id")
    val groundsAllowed: List<RouteGroundEntity>?,
    @Id
    @GeneratedValue
    @Column(name = "id")
    val id: Int
)

fun Filter.toFilterEntity() = FilterEntity(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDate(),
    typesAllowed = typesAllowed?.map { it.toRouteTypeEntity() },
    groundsAllowed = groundsAllowed?.map { it.toRouteGroundEntity() },
    id = id
)

fun FilterEntity.toFilter() = Filter(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDuration(),
    typesAllowed = typesAllowed?.map { it.toRouteType() },
    groundsAllowed = groundsAllowed?.map { it.toRouteGround() },
    id = id
)