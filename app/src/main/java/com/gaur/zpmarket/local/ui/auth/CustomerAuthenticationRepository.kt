package com.gaur.zpmarket.local.ui.auth

import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginBody
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterBody
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class CustomerAuthenticationRepository(private val customerRetrofitInterface: CustomerRetrofitInterface) {

    suspend fun login(customerLoginBody: CustomerLoginBody): Result<CustomerRegistrationResponse> {
        return SafeApiRequest.handleApiCall {
            customerRetrofitInterface.customerLogin(
                customerLoginBody
            )
        }
    }

    suspend fun registration(customerRegisterBody: CustomerRegisterBody): Result<CustomerRegistrationResponse> {
        return SafeApiRequest.handleApiCall {
            customerRetrofitInterface.customerRegistration(
                customerRegisterBody
            )
        }
    }
}
