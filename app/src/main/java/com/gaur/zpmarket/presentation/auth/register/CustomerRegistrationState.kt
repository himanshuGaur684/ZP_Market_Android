package com.gaur.zpmarket.presentation.auth.register

import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse

data class CustomerRegistrationState(
    val error: String = "",
    val data: CustomerRegistrationResponse? = null,
    val isLoading: Boolean = false
)