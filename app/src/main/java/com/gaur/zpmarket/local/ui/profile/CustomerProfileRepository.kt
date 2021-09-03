package com.gaur.zpmarket.local.ui.profile

import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileResponse
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class CustomerProfileRepository(private val customerRetrofitInterface: CustomerRetrofitInterface) {


    suspend fun getCustomerProfile(customerId: String): Result<CustomerProfileResponse> {
        return SafeApiRequest.handleApiCall {
            customerRetrofitInterface.getCustomerProfile(
                customerId
            )
        }
    }


}