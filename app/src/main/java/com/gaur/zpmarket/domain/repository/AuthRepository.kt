package com.gaur.zpmarket.domain.repository

import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import retrofit2.Response

interface AuthRepository {

    suspend fun login(customerLoginDTO: CustomerLoginDTO): Response<CustomerRegistrationResponse>

    suspend fun registration(customerRegisterDTO: CustomerRegisterDTO): Response<CustomerRegistrationResponse>


}