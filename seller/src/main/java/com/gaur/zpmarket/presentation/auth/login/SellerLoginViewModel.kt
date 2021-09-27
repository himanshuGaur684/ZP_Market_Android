package com.gaur.zpmarket.presentation.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.presentation.auth.AuthRepository
import com.gaur.zpmarket.remote.response_seller.login.SellerLoginObject
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationResponseObject
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SellerLoginViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val loginObservables = SellerLoginObservables(email = "", password = "")

    private val _login = MutableStateFlow<Events<Result<SellerRegistrationResponseObject>>>(
        Events(
            Result.empty()
        )
    )
    val login: StateFlow<Events<Result<SellerRegistrationResponseObject>>> = _login

    fun sellerLogin(sellerLoginObject: SellerLoginObject) = viewModelScope.launch {
        _login.value = Events(Result(Status.LOADING, null, null))
        _login.value = Events(authRepository.sellerLogin(sellerLoginObject))
    }
}
