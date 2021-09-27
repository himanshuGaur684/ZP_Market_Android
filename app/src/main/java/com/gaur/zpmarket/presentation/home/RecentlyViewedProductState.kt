package com.gaur.zpmarket.presentation.home

import com.gaur.zpmarket.remote.response_customer.Product

data class RecentlyViewedProductState(
    val data: List<Product> = emptyList(),
    val isLoading: Boolean = false,
    val error: String = ""
)