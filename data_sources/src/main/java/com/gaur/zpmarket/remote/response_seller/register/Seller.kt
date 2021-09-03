package com.gaur.zpmarket.remote.response_seller.register

data class Seller(
    val __v: Int,
    val _id: String,
    val email: String,
    val isSeller: Boolean,
    val mobileNumber: String,
    val name: String,
    val password: String
)