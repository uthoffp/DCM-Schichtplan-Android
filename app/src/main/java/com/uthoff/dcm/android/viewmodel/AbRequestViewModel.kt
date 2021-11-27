package com.uthoff.dcm.android.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uthoff.dcm.android.R
import com.uthoff.dcm.android.repository.datasource.AbRequestRepository
import com.uthoff.dcm.android.repository.model.RequestDays
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

    private val _requestDays = MutableLiveData<RequestDays>()
    val requestDays: LiveData<RequestDays> = _requestDays

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
            val result = abRequestRepository.getHolidays(
                user,
                DateFormatter.enDateString(start),
                DateFormatter.enDateString(stop)
            )

            withContext(Dispatchers.Main) {
                if (result.isSuccessful) {
                    _requestDays.value = result.body();
                } else {
                    _message.value =
                        "Beim Abfragen der Urlaubsdaten ist ein Fehler aufgetreten. Bitte vversuchen sie es später erneut."
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