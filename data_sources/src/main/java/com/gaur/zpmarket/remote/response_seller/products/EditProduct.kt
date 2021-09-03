package com.gaur.zpmarket.remote.response_seller.products

data class EditProduct(
    val name: String,
    var categoryId: String,
    var description: String,
    var discount: String,
    var discountPrice: String,
    var marketPrice: String,
    var productDetails: String,
    var productFeatures: String,
    var packagingDetails: String,
    var cashOnDelivery: Boolean
)