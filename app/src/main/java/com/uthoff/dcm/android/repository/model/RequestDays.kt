package com.uthoff.dcm.android.repository.model

data class RequestDays(
    var prevYear: Double,
    var thisYear: Double,
    var total: Double,
    var corrections: Double,
    var taken: Double,
    var open: Double,
    var thisRequest: Double,
    var remaining: Double
)