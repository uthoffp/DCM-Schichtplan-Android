package com.uthoff.dcm.android.repository.datasource

import android.telecom.Call
import retrofit2.http.GET

interface Webservice {
    @GET("/")
    fun getLandingPage(): retrofit2.Call<String>
}