package com.gaur.zpmarket.remote.response_customer.auth.register

data class CustomerRegisterDTO(
    val address: String,
    val email: String,
    val mobileNumber: String,
    val name: String,
    val password: String,
    val pincode: String
)
