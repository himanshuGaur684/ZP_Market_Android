package com.gaur.zpmarket.pagination.search.response

import com.gaur.zpmarket.remote.response_customer.Product

data class SearchPagingResponse(
    val results: List<Product>
)
