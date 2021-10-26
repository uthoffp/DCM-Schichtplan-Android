package com.uthoff.dcm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import com.uthoff.dcm.android.repository.datasource.CompanyRepository
import com.uthoff.dcm.android.repository.datasource.UserRepository
import com.uthoff.dcm.android.repository.model.Company
import com.uthoff.dcm.android.repository.model.User
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

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
        getAllCompanies()
    }

    private fun getAllCompanies() {
        CoroutineScope(Dispatchers.IO).launch {
            val result = companyRepository.getAllCompanies()
            if (result.isSuccessful) {
                withContext(Dispatchers.Main) {
                    _companies.value = result.body()!!
                }
            }
        }
    }

    fun login(username: String, pw: String) {

    }

    fun validateInput(email: TextInputEditText, pw: TextInputEditText): Boolean {
        if (email.text.isNullOrBlank() || pw.text.isNullOrBlank()) {
            _errorMessage.value = "Bitte füllen sie alle Felder aus."
            return false
        }
        return true
    }
}