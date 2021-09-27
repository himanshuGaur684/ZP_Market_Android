package com.gaur.zpmarket.data.repository

import com.gaur.zpmarket.domain.repository.ProfileRepository
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileDTO
import retrofit2.Response

class ProfileRepositoryImpl constructor(private val customerRetrofitInterface: CustomerRetrofitInterface) :
    ProfileRepository {
    override suspend fun getCustomerProfile(customerId: String): Response<CustomerProfileDTO> {
        return customerRetrofitInterface.getCustomerProfile(customerId)
    }
}