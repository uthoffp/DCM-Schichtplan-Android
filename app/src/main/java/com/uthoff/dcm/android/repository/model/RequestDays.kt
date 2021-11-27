package com.uthoff.dcm.android.repository.model

data class RequestDays(
    var prevYear: Int,
    var thisYear: Int,
    var total: Int,
    var corrections: Int,
    var taken: Int,
    var open: Int,
    var thisRequest: Int,
    var remaining: Int
)