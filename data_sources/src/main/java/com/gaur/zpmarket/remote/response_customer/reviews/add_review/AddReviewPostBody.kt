package com.gaur.zpmarket.remote.response_customer.reviews.add_review

data class AddReviewPostBody(
    val comment: String,
    val customer: String,
    val rating: String,
    val date:String
)