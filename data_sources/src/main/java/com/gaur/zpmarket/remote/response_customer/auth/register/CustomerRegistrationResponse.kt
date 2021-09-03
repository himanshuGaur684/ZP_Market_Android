package com.gaur.zpmarket.remote.response_customer.auth.register

data class CustomerRegistrationResponse(
    val custumer: Customer,
    val message: String,
    val success: Boolean,
    val token: String,
    val refreshToken :String
)