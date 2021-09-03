package com.gaur.zpmarket.remote.response_customer.home

import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.Category

data class HomeResponse(
    val message: String,
    val result: Result,
    val success: Boolean
)

data class Result(
    val categories: List<Category>,
    val newestArrived: List<Product>,
    val zp_market_assured: List<Product>
)

