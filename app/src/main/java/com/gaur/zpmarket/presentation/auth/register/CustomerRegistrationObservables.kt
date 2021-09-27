package com.gaur.zpmarket.presentation.auth.register

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gaur.zpmarket.seller.BR

class CustomerRegistrationObservables(
    name: String,
    email: String,
    password: String,
    mobileNumber: String,
    confirmPassword: String,
    address: String,
    pinCode: String
) : BaseObservable() {

    @get:Bindable
    var name: String = name
        set(value) {
            field = value
            notifyPropertyChanged(BR.name)
        }

    @get:Bindable
    var email: String = email
        set(value) {
            field = value
            notifyPropertyChanged(BR.email)
        }

    @get:Bindable
    var password: String = password
        set(value) {
            field = value
            notifyPropertyChanged(BR.password)
        }

    @get:Bindable
    var mobileNumber: String = mobileNumber
        set(value) {
            field = value
            notifyPropertyChanged(BR.mobileNumber)
        }

    @get:Bindable
    var confirmPassword: String = confirmPassword
        set(value) {
            field = value
            notifyPropertyChanged(BR.confirmPassword)
        }

    @get:Bindable
    var address: String = address
        set(value) {
            field = value
            notifyPropertyChanged(BR.address)
        }

    @get:Bindable
    var pinCode: String = pinCode
        set(value) {
            field = value
            notifyPropertyChanged(BR.pinCode)
        }
}
