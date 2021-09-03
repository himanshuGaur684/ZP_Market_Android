package com.gaur.zpmarket.local.ui.auth.registration

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gaur.zpmarket.seller.BR
import java.util.*

class RegistrationObservables(
    name: String,
    email: String,
    password: String,
    mobileNumber: String,
    confirmPassword: String
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
}
