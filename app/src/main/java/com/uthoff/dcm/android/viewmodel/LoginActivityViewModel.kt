package com.uthoff.dcm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.uthoff.dcm.android.repository.datasource.CompanyRepository
import com.uthoff.dcm.android.repository.datasource.UserRepository
import com.uthoff.dcm.android.repository.model.Company
import com.uthoff.dcm.android.repository.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.SocketException
import java.net.SocketTimeoutException

class LoginActivityViewModel {
    private val userRepository: UserRepository = UserRepository()
    private val companyRepository: CompanyRepository = CompanyRepository()

    private val _companies = MutableLiveData<List<Company>>().apply { value = emptyList() }
    val companies: LiveData<List<Company>> = _companies

    private val _user = MutableLiveData<User>()
    val user: LiveData<User> = _user

    private val _errorMessage = MutableLiveData<String>()
    val errorMessage: LiveData<String> = _errorMessage

    init {
        CoroutineScope(Dispatchers.IO).launch {
            getAllCompanies()
        }
    }

    private suspend fun getAllCompanies() {
        try {
            val result = companyRepository.getAllCompanies()
            if (result.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _companies.value = result.body()!!
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
            withContext(Dispatchers.Main) {
                _errorMessage.value = "Es konnte keine Verbindung mit dem Server hergestellt werden."
            }
            getAllCompanies()
        }
    }

    fun login(company: String, email: String, pw: String) {
        if (!validateInput(email, pw)) return
        CoroutineScope(Dispatchers.IO).launch {
            val cId: Int? = companies.value?.filter { c: Company ->
                c.CompanyName1 == company
            }?.get(0)?.ID?.toInt()

            if (cId != null) {
                val result = userRepository.login(cId, email, pw)
                withContext(Dispatchers.Main) {
                    if (result.isSuccessful) _user.value = result.body()!!
                    else _errorMessage.value = "Benutzername oder Kennwort falsch."
                }
            } else {
                _errorMessage.value =
                    "Unternehemen konnte nicht gefunden werden. Bitte versuchen sie es später erneut"
            }
        }
    }

    private fun validateInput(email: String, pw: String): Boolean {
        if (email.isBlank() || pw.isBlank()) {
            _errorMessage.value = "Bitte füllen sie alle Felder aus."
            return false
        }
        return true
    }
}