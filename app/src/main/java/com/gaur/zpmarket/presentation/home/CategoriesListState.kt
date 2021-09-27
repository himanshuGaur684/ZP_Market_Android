package com.gaur.zpmarket.presentation.home

import com.gaur.zpmarket.remote.response_customer.category.Category

data class CategoriesListState(
    val data: List<Category> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)