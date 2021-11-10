package com.uthoff.dcm.android.repository.model

import java.io.Serializable

class User(
    val company: String,
    val familyName: String,
    val firstName: String,
    val token: String,
    val uId: String,
    val department: String?
) : Serializable {
    fun fullName(): String {
        return "$firstName $familyName"
    }
}