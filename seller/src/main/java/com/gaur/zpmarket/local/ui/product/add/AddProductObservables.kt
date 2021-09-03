package com.gaur.zpmarket.local.ui.product.add

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import androidx.databinding.library.baseAdapters.BR
import com.gaur.zpmarket.remote.response_customer.Product

class AddProductObservables(
    name: String,
    description: String,
    marketPrice: String,
    discountPrice: String,
    productDetails: String,
    productFeatures: String,
    packagingDetails: String,
    cashOnDelivery: Boolean
) : BaseObservable() {

    fun initialize(product: Product) {
        name = product.name
        description = product.description
        marketPrice = product.marketPrice.toString()
        discountPrice = product.discountPrice.toString()
        productDetails = product.productDetails
        productFeatures = product.productFeatures
        packagingDetails = product.packagingDetails
        cashOnDelivery = product.cashOnDelivery
    }

    @get:Bindable
    var name: String = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var description: String = description
        set(value) {
            field = value
            notifyPropertyChanged(BR.description)
        }

    @get:Bindable
    var marketPrice: String = marketPrice
        set(value) {
            field = value
            notifyPropertyChanged(BR.marketPrice)
        }

    @get:Bindable
    var discountPrice: String = discountPrice
        set(value) {
            field = value
            notifyPropertyChanged(BR.discountPrice)
        }

    @get:Bindable
    var productDetails: String = productDetails
        set(value) {
            field = value
            notifyPropertyChanged(BR.productDetails)
        }

    @get:Bindable
    var productFeatures: String = productFeatures
        set(value) {
            field = value
            notifyPropertyChanged(BR.productFeatures)
        }

    @get:Bindable
    var packagingDetails: String = packagingDetails
        set(value) {
            field = value
            notifyPropertyChanged(BR.packagingDetails)
        }

    @get:Bindable
    var cashOnDelivery: Boolean = cashOnDelivery
        set(value) {
            field = value
            notifyPropertyChanged(BR.cashOnDelivery)
        }
}
