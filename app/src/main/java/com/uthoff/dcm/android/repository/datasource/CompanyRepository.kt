package com.uthoff.dcm.android.repository.datasource

import com.uthoff.dcm.android.repository.model.Company
import retrofit2.Response

class CompanyRepository {
    suspend fun getCompanyData(): Response<Company> {
        val token =
            "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJFbXBsb3llZU51bWJlciI6IjY4IiwiRmFtaWx5TmFtZSI6IkhlaW56IiwiRmlyc3ROYW1lIjoiVG9iaWFzIiwiUGFzc3dvcmQiOiLCsCHCsWPCsiPCs1fDoFxcw6E2w6JCw6N7IiwiQ29tcGFueSI6IjEiLCJXZWJBY2Nlc3NCbG9ja2VkIjpudWxsLCJpYXQiOjE2MzUyNjU0MjcsImV4cCI6MTYzNTI2OTAyN30.uGiY-N8jz2CvY7XvTf0Z5jsHLZ-M0K6KU60SH149VZg"
        val apiService: ApiService = ApiService.getInstance()
        return apiService.getCompanyData(1, token)
    }

    suspend fun getAllCompanies(): Response<List<Company>> {
        val apiService: ApiService = ApiService.getInstance()
        return apiService.getAllCompanies()
    }
}