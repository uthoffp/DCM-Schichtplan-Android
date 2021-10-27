package com.uthoff.dcm.android.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.uthoff.dcm.android.repository.datasource.CompanyRepository
import com.uthoff.dcm.android.repository.model.Company
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import com.uthoff.dcm.android.repository.model.User

class CompanyViewModel(private val user: User) : ViewModel() {
    private val companyRepository: CompanyRepository = CompanyRepository()
    private val companyData = MutableLiveData<Company>()
    val company: LiveData<Company> = companyData

    init {
        loadCompanyData()
    }

    private fun loadCompanyData() {
        CoroutineScope(IO).launch {
            val result = companyRepository.getCompanyData(user.company.toInt(), user.token)
            if (result.isSuccessful) {
                withContext(Main) {
                    companyData.value = result.body()!!
                }
            }
        }
    }
}