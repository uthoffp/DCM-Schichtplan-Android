package com.uthoff.dcm.android.viewmodel

import android.content.Context
import android.icu.util.TimeUnit
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.datasource.AbRequestRepository
import com.uthoff.dcm.android.repository.model.SpecialTime
import com.uthoff.dcm.android.repository.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class AbRequestViewModel(private val user: User) : ViewModel() {
    private val abRequestRepository: AbRequestRepository = AbRequestRepository()

    var type: SpecialTime? = null
    var start: Long = Date().time
    var stop: Long = Date().time
    var firstDayHalf: Int = 0
    var lastDayHalf: Int = 0
    var comment: String? = null

    private val _abTypes = MutableLiveData<List<SpecialTime>>()
    val abTypes: LiveData<List<SpecialTime>> = _abTypes

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _image = MutableLiveData<Any>()
    val image: LiveData<Any> = _image

    private val _checkResult = MutableLiveData<Map<String, Double?>>()
    val checkResult: LiveData<Map<String, Double?>> = _checkResult

    private val _isValid = MutableLiveData<Boolean>()
    val isValid: LiveData<Boolean> = _isValid

    init {
        getAbTypes()
    }

    private fun getAbTypes() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = abRequestRepository.getSpecialTimes(user)
            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    _abTypes.value = result.body()
                } else _message.value =
                    "Beim der Datenübertragung ist ein Fehler aufgetreten. Bitte versuchen sie es später erneut"
            }
        }
    }

    fun setImage(image: Any?) {
        _image.value = image
    }

    fun validateUserInput(
        type: String?,
        firstDayHalf: Int,
        lastDayHalf: Int,
        comment: String
    ): Boolean {
        if (type == null || type.isBlank()) {
            _message.value = "Bitte wählen sie einen Fehlzeit Typ aus."
            return false
        }

        if (start > stop) {
            _message.value = "Der gewählte Zeitraum ist ungültig."
            return false
        }

        this.firstDayHalf = firstDayHalf
        this.lastDayHalf = lastDayHalf
        this.type = _abTypes.value?.find { specialTime -> specialTime.Name == type }
        this.comment = comment
        checkAbRequest()

        return true
    }

    fun dayTypeToKey(dayType: String, context: Context): Int {
        if (dayType == context.getString(R.string.menu_day_half)) return 1
        return 0
    }

    fun isDirectSend(): Boolean {
        return type?.DaysInAdvance == 0
    }

    private fun checkAbRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            val year = Calendar.getInstance().get(Calendar.YEAR)
            val startDate = Date(start)
            val stopDate = Date(stop)
            val requestDays: Long = java.util.concurrent.TimeUnit.DAYS.convert(
                stopDate.time - startDate.time,
                java.util.concurrent.TimeUnit.MILLISECONDS
            ) + 1

            val holidayPlanned = abRequestRepository.holidayPlanned(user, year)
            val holidayActual = abRequestRepository.holidayActual(user, year)

            if (holidayPlanned.isSuccessful && holidayPlanned.isSuccessful) {
                withContext(Dispatchers.Main) {
                    val taken = holidayPlanned.body()!! + holidayActual.body()!!
                    val open = 25.0 - taken
                    val remaining = open - requestDays
                    _checkResult.value = mapOf(
                        "prevYear" to 0.0,
                        "thisYear" to 25.0,
                        "correction" to 0.0,
                        "total" to 25.0 + 0.0,
                        "taken" to taken,
                        "open" to 25.0 - taken,
                        "thisRequest" to requestDays.toDouble() * -1,
                        "remaining" to remaining
                    )
                    _isValid.value = remaining >= 0
                }
            }
        }
    }

    fun sendRequest() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = abRequestRepository.postAbRequest(
                user,
                DateFormatter.enDateString(start),
                firstDayHalf,
                DateFormatter.enDateString(stop),
                lastDayHalf,
                comment,
                _image.value
            )
            withContext(Dispatchers.Main) {
                if (!result.isSuccessful) _message.value =
                    "Beim senden des Fehlzeitantrags ist ein Fehler aufgetreten."
                else _message.value = "Der Antrag wurde erfolgreich übermittelt."
            }
        }
    }
}