package com.gaur.zpmarket.local.ui.auth.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gaur.zpmarket.seller.BR


class CustomerLoginObservables(email: String, password: String) : BaseObservable() {

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

}