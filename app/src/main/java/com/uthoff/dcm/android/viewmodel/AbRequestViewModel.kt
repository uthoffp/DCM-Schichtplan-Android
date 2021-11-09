package com.uthoff.dcm.android.viewmodel

import android.media.Image
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

    var type: String = ""
    var start: Long = Date().time
    var stop: Long = Date().time
    var startType: String = "1 Tag"
    var stopType: String = "1 Tag"
    var comment: String? = null
    var image: Any? = null

    private val _abTypes = MutableLiveData<List<String>>()
    val abTypes: LiveData<List<String>> = _abTypes

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

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
                } else _errorMessage.value =
                    "Beim der Datenübertragung ist ein Fehler aufgetreten. Bitte versuchen sie es später erneut"
            }
        }
    }

    fun validateUserInput(type: String?, comment: String) : Boolean {
        if(type == null || type.isBlank()) {
            _errorMessage.value = "Bitte wählen sie einen Fehlzeit Typ aus."
            return false
        }

        if(start > stop) {
            _errorMessage.value = "Der gewählte Zeitraum ist ungültig."
            return false
        }

        this.type = type
        this.comment = comment
        return true
    }
}