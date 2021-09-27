package com.gaur.zpmarket.presentation.profile

import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileDTO

data class CustomerProfileState(
    val data: CustomerProfileDTO? = null,
    val error: String = "",
    val isLoading: Boolean = false
)