package com.uthoff.dcm.android.viewmodel

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class Utils {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun dateGetTimeString(date: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date)
            return dateGetTimeString(date.time)
        }

        @SuppressLint("SimpleDateFormat")
        fun dateGetDateString(date: String): String {
            val date = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(date)
            return dateGetDateString(date.time)
        }

        fun dateGetTimeString(date: Long): String {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date), TimeZone
                    .getDefault().toZoneId()
            );
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return formatter.format(localDateTime) + " Uhr"
        }

        fun dateGetDateString(date: Long): String {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date), TimeZone
                    .getDefault().toZoneId()
            );
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return formatter.format(localDateTime)
        }
    }
}