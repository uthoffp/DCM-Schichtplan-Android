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

class AbRequestViewModel(private val user: User) : ViewModel() {
    private val abRequestRepository: AbRequestRepository = AbRequestRepository()

    private var start: Long = 0
    private var stop: Long = 0
    private var startType: String = "1 Tag"
    private var stopType: String = "1 Tag"
    private var comment: String? = null
    private var image: Image? = null

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
}