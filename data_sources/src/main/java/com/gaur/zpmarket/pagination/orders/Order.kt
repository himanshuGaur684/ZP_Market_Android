package com.gaur.zpmarket.pagination.orders

import com.gaur.zpmarket.remote.response_customer.Product
import com.google.gson.annotations.SerializedName

data class Order(
    val __v: Int,
    val _id: String,
    val address: String,
    val amountPaid: Int,
    val cashOnDelivery: Boolean,
    val createdAt: String,
    val customerId: String,
    val mobileNumber: String,
    @SerializedName("productId")
    val product: Product?,
    val productQuantity: Int,
    val time: String,
    val transactionID: String,
    val updatedAt: String
)