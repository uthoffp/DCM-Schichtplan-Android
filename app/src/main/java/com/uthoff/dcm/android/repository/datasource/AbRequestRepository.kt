package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.RequestDays
import com.uthoff.dcm.android.repository.model.SpecialTime
import com.uthoff.dcm.android.repository.model.User
import org.json.JSONObject
import retrofit2.Response

class AbRequestRepository {
    suspend fun getSpecialTimes(user: User): Response<List<SpecialTime>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.specialTime(user.company.toInt(), user.token)
    }

    suspend fun postAbRequest(
        user: User,
        start: String,
        startHalf: Int,
        stop: String,
        stopHalf: Int,
        comment: String?,
        attachment: Any?
    ): Response<Void> {
        val apiService: ApiService = ApiService.getInstance()
        val json = JSONObject()
        json.put("department", user.department)
        json.put("type", startHalf) //TODO set correct type
        json.put("start", start)
        json.put("startHalf", startHalf)
        json.put("stop", stop)
        json.put("stopHalf", stopHalf)
        json.put("comment", comment)
        //json.put("attachment", attachment)
        json.put("username", user.fullName())
        return apiService.abrequest(user.company.toInt(), user.uId.toInt(), user.token, json)
    }

    suspend fun getHolidays(user: User, start: String, stop: String):Response<RequestDays> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.holiday(user.company.toInt(), user.uId.toInt(), start, stop, user.token )
    }
}