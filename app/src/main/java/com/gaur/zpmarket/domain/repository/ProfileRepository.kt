package com.gaur.zpmarket.domain.repository

import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileDTO
import retrofit2.Response

interface ProfileRepository {
    suspend fun getCustomerProfile(customerId: String): Response<CustomerProfileDTO>
}