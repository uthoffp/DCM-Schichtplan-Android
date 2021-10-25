package com.uthoff.dcm.android.repository.datasource

import retrofit2.http.GET

interface Webservice {
    @GET("/")
    fun getLandingPage(): retrofit2.Call<String>
}