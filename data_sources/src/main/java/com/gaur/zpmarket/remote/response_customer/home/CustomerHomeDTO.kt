package com.gaur.zpmarket.remote.response_customer.home

import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.Category

data class CustomerHomeDTO(
    val message: String,
    val result: Details,
    val success: Boolean
)

data class Details(
    val categories: List<Category>,
    val newestArrived: List<Product>,
    val zp_market_assured: List<Product>
)
