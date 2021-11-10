package com.uthoff.dcm.android.viewmodel

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class DateFormatter {
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
            )
            val formatter = DateTimeFormatter.ofPattern("HH:mm")
            return formatter.format(localDateTime) + " Uhr"
        }

        fun dateGetDateString(date: Long): String {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date), TimeZone
                    .getDefault().toZoneId()
            )
            val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
            return formatter.format(localDateTime)
        }

        fun getFirstDayOfWeek(time: Long): Long {
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = Date(time)
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
            return calendar.timeInMillis
        }

        private fun dateNoYearString(date: Long): String {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(date), TimeZone
                    .getDefault().toZoneId()
            )
            val formatter = DateTimeFormatter.ofPattern("dd.MM")
            return formatter.format(localDateTime)
        }

        private fun weekDayShort(day: Int): String {
            when(day) {
                Calendar.MONDAY -> return "Mo."
                Calendar.TUESDAY -> return "Di."
                Calendar.WEDNESDAY -> return "Mi."
                Calendar.THURSDAY -> return "Do."
                Calendar.FRIDAY -> return "Fr."
                Calendar.SATURDAY -> return "Sa."
                Calendar.SUNDAY -> return "So."
            }
            return ""
        }

        @SuppressLint("SimpleDateFormat")
        fun dayDateString(strDate: String): String {
            val time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'").parse(strDate)
            val calendar: Calendar = Calendar.getInstance()
            calendar.time = time
            val day: String = weekDayShort(calendar.get(Calendar.DAY_OF_WEEK))
            return "${day}, ${dateNoYearString(time.time)}"
        }

        fun enDateString(time: Long): String {
            val localDateTime: LocalDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(time), TimeZone
                    .getDefault().toZoneId()
            )
            val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
            return formatter.format(localDateTime)
        }
    }
}