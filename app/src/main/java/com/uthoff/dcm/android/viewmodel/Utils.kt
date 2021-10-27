package com.uthoff.dcm.android.viewmodel

import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        fun dateGetTimeString(date: String): String {
            val localDateTime: LocalDateTime = LocalDateTime.parse(date)
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return formatter.format(localDateTime) + " Uhr"
        }

        fun dateGetDateString(date: String): String? {
            val localDateTime: LocalDateTime = LocalDateTime.parse(date)
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return formatter.format(localDateTime)
        }

        fun dateGetTimeString(date: Long): String {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date), TimeZone
                    .getDefault().toZoneId()
            );
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return formatter.format(localDateTime) + " Uhr"
        }

        fun dateGetDateString(date: Long): String? {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date), TimeZone
                    .getDefault().toZoneId()
            );
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return formatter.format(localDateTime)
        }
    }
}