package com.gaur.zpmarket.local.ui.product.edit_product


object EditProductUtils {
    fun validateData(
        name: String,
        productDescription: String,
        categoryId: String,
        marketPrice: String,
        discountPrice: String,
        packaging: String,
        productDetails: String,
        productFeatures: String
    ): String {
        return if (name.isEmpty() || productDescription.isEmpty() || categoryId.isEmpty() || marketPrice.isEmpty() || discountPrice.isEmpty() ||
            packaging.isEmpty() || productDetails.isEmpty() || productFeatures.isEmpty()
        ) {
            "Please fill all fields"
        } else if (name.length < 20) {
            "Product name must be greater than 20 words"
        } else if (productDescription.length < 40) {
            "Product Description must be greater than 40 words"
        } else if (marketPrice.toInt() < discountPrice.toInt()) {
            "Please fill correct prices"
        } else if (packaging.length < 20) {
            "Please fill at least 20 words in Packaging"
        } else if (productFeatures.length < 20) {
            "Please fill at least 20 words in Product Features"
        } else {
            ""
        }
    }
}

