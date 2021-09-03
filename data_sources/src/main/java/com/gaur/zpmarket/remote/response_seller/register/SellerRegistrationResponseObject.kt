package com.gaur.zpmarket.remote.response_seller.register

data class SellerRegistrationResponseObject(
    val message: String,
    val seller: Seller,
    val success: Boolean,
    val token: String,
    val refreshToken:String
)