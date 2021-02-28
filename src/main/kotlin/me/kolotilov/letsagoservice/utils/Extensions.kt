package me.kolotilov.letsagoservice.utils

import org.joda.time.DateTime
import org.joda.time.Duration
import java.util.*

fun Date.toDateTime() = DateTime(this)

fun Duration.toDate() = Date(millis)

fun Date.toDuration() = Duration(time)