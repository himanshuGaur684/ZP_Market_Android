package com.gaur.zpmarket.local.ui.auth.registration

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.local.ui.auth.AuthRepository
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenBody
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenResponse
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationObject
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
class RegistrationViewModel @Inject constructor(private val authRepository: AuthRepository) :
    ViewModel() {

    val registrationObservables = RegistrationObservables(
        name = "",
        password = "",
        email = "",
        mobileNumber = "",
        confirmPassword = ""
    )

    private val _refreshToken =
        MutableStateFlow<Events<Result<RefreshTokenResponse>>>(Events(Result.empty()))
    val refreshToken: StateFlow<Events<Result<RefreshTokenResponse>>> = _refreshToken

    private val _registration = MutableStateFlow<Events<Result<SellerRegistrationResponseObject>>>(
        Events(Result.empty())
    )
    val registration: StateFlow<Events<Result<SellerRegistrationResponseObject>>> = _registration

    fun sellerRegistration(sellerRegistrationObject: SellerRegistrationObject) =
        viewModelScope.launch {
            _registration.value = Events(Result(Status.LOADING, null, null))
            _registration.value =
                Events(authRepository.sellerRegistration(sellerRegistrationObject))
        }

    fun refreshToken(refreshToken: String) = viewModelScope.launch {
        _refreshToken.value = Events(Result(Status.LOADING, null, null))
        _refreshToken.value =
            Events(authRepository.refreshToken(RefreshTokenBody(refreshToken)))
    }
}
