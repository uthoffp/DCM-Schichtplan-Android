package com.uthoff.dcm.android.repository.datasource
import com.uthoff.dcm.android.repository.model.TimeInfo
import com.uthoff.dcm.android.repository.model.User
import retrofit2.Response

class TimeRepository {
    suspend fun timeActual(user: User, start: String, end: String): Response<List<TimeInfo>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.timeActual(user.company.toInt(), user.uId.toInt(), start, end, user.token)
    }

    suspend fun timePlanned(user: User, start: String, end: String): Response<List<TimeInfo>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.timePlanned(user.company.toInt(), user.uId.toInt(), start, end, user.token)
    }
}