package com.gaur.zpmarket.remote.response_seller.products

import com.gaur.zpmarket.remote.response_customer.Product

data class SellerProductResponse(
    val results: List<Product>
)