package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.ClockingTime
import com.uthoff.dcm.android.repository.model.Company
import com.uthoff.dcm.android.repository.model.TimeInfo
import com.uthoff.dcm.android.repository.model.User
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface ApiService {

    @GET("/company")
    suspend fun getAllCompanies(): Response<List<Company>>

    @GET("/company/{cId}")
    suspend fun getCompanyData(
        @Path("cId") cId: Int,
        @Header("auth") token: String,
    ): Response<Company>

    @GET("/company/{cId}/login/{email}")
    suspend fun login(
        @Path("cId") cId: Int,
        @Path("email") email: String,
        @Query("pw") pw: String
    ): Response<User>

    @POST("/company/{cId}/user/{uId}/clocking")
    suspend fun clocking(
        @Path("cId") cId: Int,
        @Path("uId") uId: Int,
        @Header("auth") token: String,
        @Body body: JSONObject
    ): Response<Void>

    @GET("/company/{cId}/user/{uId}/actual/latest")
    suspend fun latestTimes(
        @Path("cId") cId: Int,
        @Path("uId") uId: Int,
        @Header("auth") token: String
    ): Response<List<ClockingTime>>

    @GET("/company/{cId}/user/{uId}/actual/{start}/{end}")
    suspend fun timeActual(
        @Path("cId") cId: Int,
        @Path("uId") uId: Int,
        @Path("start") start: String,
        @Path("end") end: String,
        @Header("auth") token: String
    ): Response<List<TimeInfo>>

    @GET("/company/{cId}/user/{uId}/planned/{start}/{end}")
    suspend fun timePlanned(
        @Path("cId") cId: Int,
        @Path("uId") uId: Int,
        @Path("start") start: String,
        @Path("end") end: String,
        @Header("auth") token: String
    ): Response<List<TimeInfo>>

    companion object {
        var apiService: ApiService? = null
        fun getInstance(): ApiService {
            if (apiService == null) {
                val retrofit = Retrofit.Builder()
                    .baseUrl("http://192.168.178.110:8080")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                    .build()
                apiService = retrofit.create(ApiService::class.java)
            }
            return apiService!!
        }

    }
}
