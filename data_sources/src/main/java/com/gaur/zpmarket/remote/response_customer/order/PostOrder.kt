package com.gaur.zpmarket.remote.response_customer.order

data class PostOrder(
    val address: String,
    val amountPaid: Int,
    val cashOnDelivery: Boolean,
    val customerId: String,
    val mobileNumber: String,
    val productId: String,
    val productQuantity: Int,
    val time: String,
    val transactionID: String
)