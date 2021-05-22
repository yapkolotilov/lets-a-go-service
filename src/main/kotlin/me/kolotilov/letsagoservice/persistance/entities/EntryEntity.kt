package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Entry
import javax.persistence.*

@Entity(name = "entry")
data class EntryEntity(
        @OneToMany(cascade = [CascadeType.ALL])
        @JoinColumn(name = "entry_id")
        val points: List<PointEntity>,
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id: Int
)

fun Entry.toEntryEntity() = EntryEntity(
        points = points.map { it.toPointEntity() },
        id = id
)

fun EntryEntity.toEntry() = Entry(
        points = points.map { it.toPoint() },
        id = id
)