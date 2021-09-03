package com.gaur.zpmarket.remote.response_customer.category

data class CategoryResponse(
    val category: List<Category>,
    val message: String,
    val success: Boolean
)