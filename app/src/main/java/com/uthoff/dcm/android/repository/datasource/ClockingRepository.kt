package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.ClockingTimes
import com.uthoff.dcm.android.repository.model.User
import org.json.JSONObject
import retrofit2.Response

class ClockingRepository {
    private val TERMINAL_ID = 1101

    suspend fun clocking(user: User, status: Int): Response<Void> {
        val apiService: ApiService = ApiService.getInstance()
        val json = JSONObject()
        json.put("tId", TERMINAL_ID)
        json.put("status", status)
        json.put("username", user.fullName())
        return apiService.clocking(user.company.toInt(), user.uId.toInt(), user.token, json)
    }

    suspend fun latestTimes(user: User): Response<List<ClockingTimes>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.latestTimes(user.company.toInt(), user.uId.toInt(), user.token)
    }
}