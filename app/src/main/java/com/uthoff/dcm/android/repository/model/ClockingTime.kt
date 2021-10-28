package com.uthoff.dcm.android.repository.model

data class ClockingTime(
    var E_Date: String,
    val E_Status: Int,
    var E_Time: String,
    val EmployeeNumber: String,
    var E_StatusText: String
)