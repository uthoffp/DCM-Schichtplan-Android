package com.uthoff.dcm.android.repository.model

data class User(
    val company: String,
    val familyName: String,
    val firstName: String,
    val token: String,
    val uId: String
)