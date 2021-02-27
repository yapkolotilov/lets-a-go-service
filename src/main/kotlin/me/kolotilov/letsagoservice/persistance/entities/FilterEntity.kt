package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Filter
import org.joda.time.Duration
import javax.persistence.*

@Entity(name = "filter")
data class FilterEntity(
    @Column(name = "max_length")
    val maxLength: Double?,
    @Column(name = "max_duration")
    val maxDuration: Duration?,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "filter_id")
    val typesAllowed: List<RouteTypeEntity>?,
    @OneToMany(cascade = [CascadeType.ALL])
    @JoinColumn(name = "filter_id")
    val groundsAllowed: List<RouteGroundEntity>?,
    @Id
    @Column(name = "id")
    val id: Int
)

fun Filter.toFilterEntity() = FilterEntity(
    maxLength = maxLength,
    maxDuration = maxDuration,
    typesAllowed = typesAllowed?.map { it.toRouteTypeEntity() },
    groundsAllowed = groundsAllowed?.map { it.toRouteGroundEntity() },
    id = id
)

fun FilterEntity.toFilter() = Filter(
    maxLength = maxLength,
    maxDuration = maxDuration,
    typesAllowed = typesAllowed?.map { it.toRouteType() },
    groundsAllowed = groundsAllowed?.map { it.toRouteGround() },
    id = id
)