package com.gaur.zpmarket.pagination.new_products

import com.gaur.zpmarket.remote.response_customer.Product

data class NewestProductPagingResponse(
    val results: List<Product>
)
