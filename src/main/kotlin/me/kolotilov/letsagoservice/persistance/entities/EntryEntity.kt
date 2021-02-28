package me.kolotilov.letsagoservice.persistance.entities

import me.kolotilov.letsagoservice.domain.models.Entry
import me.kolotilov.letsagoservice.utils.toDate
import me.kolotilov.letsagoservice.utils.toDateTime
import me.kolotilov.letsagoservice.utils.toDuration
import java.util.*
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity(name = "entry")
data class EntryEntity(
        @Column(name = "timestamp")
        val timestamp: Date,
        @Column(name = "duration")
        val duration: Date,
        @Column(name = "finished")
        val finished: Boolean,
        @Id
        @GeneratedValue
        @Column(name = "id")
        val id: Int
)

fun Entry.toEntryEntity() = EntryEntity(
        timestamp = timestamp.toDate(),
        duration = duration.toDate(),
        finished = finished,
        id = id
)

fun EntryEntity.toEntry() = Entry(
        timestamp = timestamp.toDateTime(),
        duration = duration.toDuration(),
        finished = finished,
        id = id
)