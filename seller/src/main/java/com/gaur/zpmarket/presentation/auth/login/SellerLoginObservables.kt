package com.gaur.zpmarket.presentation.auth.login

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.gaur.zpmarket.seller.BR

class SellerLoginObservables(email: String, password: String) : BaseObservable() {

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
