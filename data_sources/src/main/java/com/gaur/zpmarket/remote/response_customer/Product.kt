package com.gaur.zpmarket.remote.response_customer

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Parcelize
@Entity
data class Product(
    val __v: Int,
    @PrimaryKey(autoGenerate = false)
    val _id: String,
    val categoryId: String,
    val description: String,
    val discount: String,
    val discountPrice: Int,
    val imageUrl: List<String>,
    val marketPrice: Int,
    val name: String,
    val platformAssured: Boolean,
    val rating: Double,
    val reviews: Int,
    val sellerId: String,
    val productDetails:String,
    val productFeatures:String,
    val packagingDetails:String,
    val cashOnDelivery:Boolean,
    val stocks:Int=-1
) : Parcelable