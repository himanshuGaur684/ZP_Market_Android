package com.gaur.zpmarket.presentation.home

import com.gaur.zpmarket.remote.response_customer.home.CustomerHomeDTO

data class CustomerHomeState(
    val isLoading: Boolean = false,
    val data: CustomerHomeDTO? = null,
    val error: String = ""
)