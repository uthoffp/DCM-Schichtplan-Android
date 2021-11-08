package com.uthoff.dcm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uthoff.dcm.android.repository.datasource.TimeRepository
import com.uthoff.dcm.android.repository.model.TimeInfo
import com.uthoff.dcm.android.repository.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class TimeViewModel(private val user: User, private val timeType: String) : ViewModel() {

    private val timeRepository: TimeRepository = TimeRepository()

    private val calendar: Calendar = Calendar.getInstance()
    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _times = MutableLiveData<List<TimeInfo>>()
    val times: LiveData<List<TimeInfo>> = _times

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    init {
        setDate(Date().time)
        getTimes()
    }

    fun setDate(time: Long) {
        calendar.timeInMillis = Utils.getFirstDayOfWeek(time)
        _date.value = Utils.dateGetDateString(calendar.timeInMillis)
        getTimes()
    }

    fun nextWeek() {
        calendar.add(Calendar.DATE, 7)
        _date.value = Utils.dateGetDateString(calendar.timeInMillis)
        getTimes()
    }

    fun prevWeek() {
        calendar.add(Calendar.DATE, -7)
        _date.value = Utils.dateGetDateString(calendar.timeInMillis)
        getTimes()
    }

    private fun getTimes() {
        _isLoading.value = true
        CoroutineScope(Dispatchers.IO).launch {
            val start: String = Utils.enDateString(calendar.timeInMillis)
            calendar.add(Calendar.DATE, 6)
            val end: String = Utils.enDateString(calendar.timeInMillis)
            calendar.add(Calendar.DATE, -6)

            val result = if (timeType == "planned") {
                timeRepository.timePlanned(user, start, end)
            } else {
                timeRepository.timeActual(user, start, end)
            }

            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    _times.value = result.body()
                } else {
                    _errorMessage.value = "Beim abrufen der Daten ist ein Fehler aufgetreten."
                }
                _isLoading.value = false
            }
        }
    }

    fun getShift1Dep(timeInfo: TimeInfo): String {
        val station: String? = timeInfo.Station1
        var department: String? = timeInfo.DivDepartmentsSt1

        if(department == null || department.isBlank()) department = timeInfo.Department

        return when {
            department == null -> ""
            station == null -> department
            else -> "$department ($station)"
        }.filter { !it.isWhitespace() }
    }

    fun getShift2Dep(timeInfo: TimeInfo): String {
        val station: String? = timeInfo.Station2
        var department: String? = timeInfo.DivDepartmentsSt2

        if(department == null || department.isBlank()) department = timeInfo.Department

        return when {
            department == null -> ""
            station == null -> department
            else -> "$department ($station)"
        }.filter { !it.isWhitespace() }
    }
}