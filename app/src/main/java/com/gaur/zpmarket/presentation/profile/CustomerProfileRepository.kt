package com.gaur.zpmarket.presentation.profile

import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileDTO
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class CustomerProfileRepository(private val customerRetrofitInterface: CustomerRetrofitInterface) {

    suspend fun getCustomerProfile(customerId: String): Result<CustomerProfileDTO> {
        return SafeApiRequest.handleApiCall {
            customerRetrofitInterface.getCustomerProfile(
                customerId
            )
        }
    }
}
