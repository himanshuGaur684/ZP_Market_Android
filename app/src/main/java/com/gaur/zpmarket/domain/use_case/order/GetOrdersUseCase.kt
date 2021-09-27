package com.gaur.zpmarket.domain.use_case.order

import android.content.SharedPreferences
import com.gaur.zpmarket.domain.repository.OrderRepository
import com.gaur.zpmarket.utils.CustomerConstants
import javax.inject.Inject

class GetOrdersUseCase @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val orderRepository: OrderRepository
) {


    fun invoke(
        customerId: String = sharedPreferences.getString(CustomerConstants.CUSTOMER_ID, "")
            .toString()
    ) = orderRepository.getPaginatedOrder(customerId)

}