package com.gaur.zpmarket.presentation.auth

import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class CustomerAuthenticationRepository(private val customerRetrofitInterface: CustomerRetrofitInterface) {

    suspend fun login(customerLoginDTO: CustomerLoginDTO): Result<CustomerRegistrationResponse> {
        return SafeApiRequest.handleApiCall {
            customerRetrofitInterface.customerLogin(
                customerLoginDTO
            )
        }
    }

    suspend fun registration(customerRegisterDTO: CustomerRegisterDTO): Result<CustomerRegistrationResponse> {
        return SafeApiRequest.handleApiCall {
            customerRetrofitInterface.customerRegistration(
                customerRegisterDTO
            )
        }
    }
}
