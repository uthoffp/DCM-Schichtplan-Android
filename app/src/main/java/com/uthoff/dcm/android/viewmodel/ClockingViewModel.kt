package com.uthoff.dcm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uthoff.dcm.android.repository.datasource.ClockingRepository
import com.uthoff.dcm.android.repository.model.ClockingTime
import com.uthoff.dcm.android.repository.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class ClockingViewModel(private val user: User) : ViewModel() {
    private val clockingRepository: ClockingRepository = ClockingRepository()

    private val _date = MutableLiveData<String>()
    val date: LiveData<String> = _date

    private val _time = MutableLiveData<String>()
    val time: LiveData<String> = _time

    private val _clockingTimes = MutableLiveData<List<ClockingTime>>()
    val clockingTime: LiveData<List<ClockingTime>> = _clockingTimes

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    private val _loading = MutableLiveData<Boolean>()
    val loading: LiveData<Boolean> = _loading

    init {
        getLatestTimes()
        getDate()
    }

    private fun getDate() {
        val date = Date().time
        _date.value = Utils.dateGetDateString(date)
        _time.value = Utils.dateGetTimeString(date)
    }

    fun clocking(status: Int) {
        CoroutineScope(IO).launch {
            val result = clockingRepository.clocking(user, status)
            withContext(Main) {
                if (result.isSuccessful) {
                    getLatestTimes()
                } else _errorMessage.value =
                    "Beim Stempeln ist ein Fehler aufgetreten. Bitte versuchen sie es später erneut"
            }
        }
    }

    fun latestStatusEquals(status: Int): Boolean {
        val latest: Int? = clockingTime.value?.last()?.E_Status
        return latest == status
    }

    private fun getLatestTimes() {
        CoroutineScope(IO).launch {
            val result = clockingRepository.latestTimes(user)
            withContext(Main) {
                if (result.isSuccessful) {
                    _clockingTimes.value = processClockingTimes(result.body()!!)
                } else _errorMessage.value = "Bei der Datenabfrage ist ein Fehler aufgetreten."
            }
        }
    }

    private fun processClockingTimes(clockingTimes: List<ClockingTime>): List<ClockingTime> {
        clockingTimes.forEach {
            it.E_Date = Utils.dateGetDateString(it.E_Date)
            it.E_Time += " Uhr"

            if (it.E_Status == 1) it.E_StatusText = "Kommt"
            else it.E_StatusText = "Geht"
        }
        return clockingTimes
    }


}