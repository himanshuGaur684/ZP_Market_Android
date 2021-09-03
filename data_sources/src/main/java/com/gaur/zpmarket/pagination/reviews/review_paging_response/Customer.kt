package com.gaur.zpmarket.pagination.reviews.review_paging_response

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Customer(
    val _id: String,
    val name: String
): Parcelable