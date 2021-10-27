package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.User
import retrofit2.Response

class UserRepository {
    suspend fun login(cId: Int, email: String, pw: String) : Response<User> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.login(cId, email, pw)
    }
}