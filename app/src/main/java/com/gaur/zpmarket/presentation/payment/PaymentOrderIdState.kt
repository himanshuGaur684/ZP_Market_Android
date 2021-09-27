package com.gaur.zpmarket.presentation.payment

import com.gaur.zpmarket.remote.response_customer.payment.OrderIdDTO

data class PaymentOrderIdState(
    val data: OrderIdDTO? = null,
    val error: String = "",
    val isLoading: Boolean = false
)