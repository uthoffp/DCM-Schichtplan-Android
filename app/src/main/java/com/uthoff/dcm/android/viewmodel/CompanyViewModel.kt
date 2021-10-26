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

class CompanyViewModel(private val companyRepository: CompanyRepository) : ViewModel() {
    private val companyData = MutableLiveData<Company>()
    val company: LiveData<Company> = companyData

    init {
        loadCompanyData()
    }

    private fun loadCompanyData() {
        CoroutineScope(IO).launch {
            val result = companyRepository.getCompanyData()
            if(result.isSuccessful) {
                withContext(Main) {
                    companyData.value = result.body()!!
                }
            }
        }
    }
}