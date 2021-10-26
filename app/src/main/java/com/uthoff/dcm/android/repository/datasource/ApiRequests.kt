package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.Company
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ApiRequests {
    @GET("/")
    fun getLandingPage(): Call<String>

    @GET("/company/{cId}")
    fun getCompanyData(
        @Path("cId") cId: Int,
        @Header("auth") token: String)
    : Call<Company>
}