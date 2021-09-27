package com.gaur.zpmarket.data.repository

import com.gaur.zpmarket.domain.repository.AuthRepository
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import retrofit2.Response

class AuthRepositoryImpl(private val customerRetrofitInterface: CustomerRetrofitInterface) :
    AuthRepository {

    override suspend fun login(customerLoginDTO: CustomerLoginDTO): Response<CustomerRegistrationResponse> {
        return customerRetrofitInterface.customerLogin(customerLoginDTO)
    }

    override suspend fun registration(customerRegisterDTO: CustomerRegisterDTO): Response<CustomerRegistrationResponse> {
        return customerRetrofitInterface.customerRegistration(customerRegisterDTO)
    }

}