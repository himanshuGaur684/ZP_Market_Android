package com.gaur.zpmarket.pagination.reviews.review_paging_response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Review(
    val __v: Int,
    val _id: String,
    val comment: String,
    val customer: Customer,
    val date: String,
    val productId: String,
    val rating: Double
) : Parcelable
