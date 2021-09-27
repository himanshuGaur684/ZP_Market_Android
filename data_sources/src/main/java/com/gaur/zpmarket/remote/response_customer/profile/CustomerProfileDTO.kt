package com.gaur.zpmarket.remote.response_customer.profile

import com.gaur.zpmarket.remote.response_customer.auth.register.Customer

data class CustomerProfileDTO(
    val customer: Customer,
    val message: String,
    val success: Boolean
)
