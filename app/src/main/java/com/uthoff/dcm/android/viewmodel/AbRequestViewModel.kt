package com.uthoff.dcm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
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

    var type: String? = null
    var start: Long = Date().time
    var stop: Long = Date().time
    var startType: String = "1 Tag"
    var stopType: String = "1 Tag"
    var comment: String? = null

    private val _abTypes = MutableLiveData<List<String>>()
    val abTypes: LiveData<List<String>> = _abTypes

    private val _message = MutableLiveData<String>()
    val message: LiveData<String> = _message

    private val _image = MutableLiveData<Any>()
    val image: LiveData<Any> = _image

    private val _checkResult = MutableLiveData<Map<String, Double>>()
    val checkResult: LiveData<Map<String, Double>> = _checkResult

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
                    _abTypes.value =
                        result.body()?.map { specialTime: SpecialTime -> specialTime.Name }
                } else _message.value =
                    "Beim der Datenübertragung ist ein Fehler aufgetreten. Bitte versuchen sie es später erneut"
            }
        }
    }

    fun setImage(image: Any?) {
        _image.value = image
    }

    fun validateUserInput(type: String?, comment: String): Boolean {
        if (type == null || type.isBlank()) {
            _message.value = "Bitte wählen sie einen Fehlzeit Typ aus."
            return false
        }

        if (start > stop) {
            _message.value = "Der gewählte Zeitraum ist ungültig."
            return false
        }

        this.type = type
        this.comment = comment
        return true
    }

    fun sendRequest() {

    }
}