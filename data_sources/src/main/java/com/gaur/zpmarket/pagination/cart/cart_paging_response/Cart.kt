package com.gaur.zpmarket.pagination.cart.cart_paging_response

import com.gaur.zpmarket.remote.response_customer.Product

data class Cart(
    val __v: Int,
    val _id: String,
    val customer: String,
    val date: String,
    val product: Product
)