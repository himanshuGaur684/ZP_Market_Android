package com.gaur.zpmarket.presentation.auth.login

import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse

data class CustomerLoginState(
    val isLoading: Boolean = false,
    val error: String = "",
    val data: CustomerRegistrationResponse? = null
)