package com.gaur.zpmarket.remote.response_customer.category

data class CategoryDTO(
    val category: List<Category>,
    val message: String,
    val success: Boolean
)
