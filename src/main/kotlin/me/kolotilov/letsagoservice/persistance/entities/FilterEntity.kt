package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Filter
import me.kolotilov.letsagoservice.domain.models.Route
import me.kolotilov.letsagoservice.utils.toDate
import me.kolotilov.letsagoservice.utils.toDuration
import java.util.*
import javax.persistence.*

@Entity(name = "filter")
data class FilterEntity(
    @Column(name = "min_length")
    val minLength: Double?,
    @Column(name = "max_length")
    val maxLength: Double?,
    @Column(name = "min_duration")
    val minDuration: Date?,
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
    minLength = length?.start,
    maxLength = length?.endInclusive,
    minDuration = duration?.start?.toDate(),
    maxDuration = duration?.endInclusive?.toDate(),
    typesAllowed = typesAllowed,
    groundsAllowed = groundsAllowed,
    id = id
)

fun FilterEntity.toFilter(): Filter {
    val length = if (minLength != null && maxLength != null) minLength..maxLength else null
    val duration =
        if (minDuration != null && maxDuration != null) minDuration.toDuration()..maxDuration.toDuration() else null
    return Filter(
        length = length,
        duration = duration,
        typesAllowed = typesAllowed,
        groundsAllowed = groundsAllowed,
        id = id
    )
}