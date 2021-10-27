package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.Company
import com.uthoff.dcm.android.repository.model.User
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("/company")
    suspend fun getAllCompanies() : Response<List<Company>>

    @GET("/company/{cId}")
    suspend fun getCompanyData(
        @Path("cId") cId: Int,
        @Header("auth") token: String
    ): Response<Company>

    @GET("/company/{cId}/login/{email}")
    suspend fun login(
        @Path("cId") cId: Int,
        @Path("email") email: String,
        @Query("pw") pw: String
    ): Response<User>

    companion object {
        var apiService: ApiService? = null
        fun getInstance() : ApiService {
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
