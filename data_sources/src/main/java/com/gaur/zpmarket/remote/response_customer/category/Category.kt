package com.gaur.zpmarket.remote.response_customer.category

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity
@Parcelize
data class Category(
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val name: String
) : Parcelable
