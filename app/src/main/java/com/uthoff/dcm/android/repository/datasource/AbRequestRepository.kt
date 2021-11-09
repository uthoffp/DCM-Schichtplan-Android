package com.uthoff.dcm.android.repository.datasource
import com.uthoff.dcm.android.repository.model.SpecialTime
import com.uthoff.dcm.android.repository.model.User
import retrofit2.Response

class AbRequestRepository {
    suspend fun getSpecialTimes(user: User): Response<List<SpecialTime>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.specialTime(user.company.toInt(), user.token)
    }
}