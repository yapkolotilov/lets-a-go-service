package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Route
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
    @ElementCollection
    @CollectionTable
    @Enumerated(EnumType.STRING)
    val typesAllowed: List<Route.Type>?,
    @ElementCollection
    @CollectionTable
    @Enumerated(EnumType.STRING)
    val groundsAllowed: List<Route.Ground>?,
    @Id
    @GeneratedValue
    @Column(name = "id")
    val id: Int
)

fun Filter.toFilterEntity() = FilterEntity(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDate(),
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed,
    id = id
)

fun FilterEntity.toFilter() = Filter(
    maxLength = maxLength,
    maxDuration = maxDuration?.toDuration(),
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed,
    id = id
)