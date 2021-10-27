package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.Company
import retrofit2.Response

class CompanyRepository {
    suspend fun getCompanyData(cId: Int, token: String): Response<Company> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.getCompanyData(cId, token)
    }

    suspend fun getAllCompanies(): Response<List<Company>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.getAllCompanies()
    }
}