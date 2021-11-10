package com.uthoff.dcm.android.repository.model

data class SpecialTime(
    val Blue: Int,
    val DaysInAdvance: Int,
    val Green: Int,
    val Name: String,
    val Red: Int,
    val ShortName: String,
    val TypeKey: Int
) {
    override fun toString(): String {
        return Name
    }
}