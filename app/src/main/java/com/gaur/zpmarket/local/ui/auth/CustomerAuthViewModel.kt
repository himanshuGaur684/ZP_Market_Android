package com.gaur.zpmarket.local.ui.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.local.ui.auth.login.CustomerLoginObservables
import com.gaur.zpmarket.local.ui.auth.register.CustomerRegistrationObservables
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginBody
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterBody
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegistrationResponse
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerAuthViewModel @Inject constructor(private val customerAuthenticationRepository: CustomerAuthenticationRepository) : ViewModel() {

    val customerAuthObservables = CustomerRegistrationObservables("", "", "", "", "", "", "")

    val customerLoginObservables = CustomerLoginObservables("", "")

    private val _login = MutableStateFlow<Events<Result<CustomerRegistrationResponse>>>(
        Events(
            Result.empty()
        )
    )
    val login: StateFlow<Events<Result<CustomerRegistrationResponse>>> = _login

    private val _registration = MutableStateFlow<Events<Result<CustomerRegistrationResponse>>>(
        Events(
            Result.empty()
        )
    )
    val registration: StateFlow<Events<Result<CustomerRegistrationResponse>>> = _registration

    fun login(loginBody: CustomerLoginBody) = viewModelScope.launch {
        _login.value = Events(Result(Status.LOADING, null, null))
        _login.value = Events(customerAuthenticationRepository.login(loginBody))
    }

    fun registration(registrationBody: CustomerRegisterBody) = viewModelScope.launch {
        _registration.value = Events(Result(Status.LOADING, null, null))
        _registration.value = Events(customerAuthenticationRepository.registration(registrationBody))
    }
}
