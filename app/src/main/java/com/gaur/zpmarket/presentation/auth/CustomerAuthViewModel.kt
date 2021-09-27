package com.gaur.zpmarket.presentation.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.use_case.auth.CustomerLoginUseCase
import com.gaur.zpmarket.domain.use_case.auth.CustomerRegistrationUseCase
import com.gaur.zpmarket.presentation.auth.login.CustomerLoginObservables
import com.gaur.zpmarket.presentation.auth.login.CustomerLoginState
import com.gaur.zpmarket.presentation.auth.register.CustomerRegistrationObservables
import com.gaur.zpmarket.presentation.auth.register.CustomerRegistrationState
import com.gaur.zpmarket.remote.response_customer.auth.login.CustomerLoginDTO
import com.gaur.zpmarket.remote.response_customer.auth.register.CustomerRegisterDTO
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomerAuthViewModel @Inject constructor(
    private val customerAuthenticationRepository: CustomerAuthenticationRepository,
    private val customerLoginUseCase: CustomerLoginUseCase,
    private val customerRegistrationUseCase: CustomerRegistrationUseCase
) :
    ViewModel() {

    val customerAuthObservables = CustomerRegistrationObservables("", "", "", "", "", "", "")

    val customerLoginObservables = CustomerLoginObservables("", "")

    private val _login = MutableStateFlow<CustomerLoginState>(CustomerLoginState())
    val login: StateFlow<CustomerLoginState> = _login

    private val _registration = MutableStateFlow<CustomerRegistrationState>(
        CustomerRegistrationState()
    )
    val registration: StateFlow<CustomerRegistrationState> = _registration

    fun login(loginDTO: CustomerLoginDTO) {
        customerLoginUseCase(loginDTO).onEach {
            when (it) {
                is Resource.Loading -> {
                    _login.value = CustomerLoginState(isLoading = true)
                }
                is Resource.Success -> {
                    _login.value = CustomerLoginState(data = it.data)
                }
                is Resource.Error -> {
                    _login.value = CustomerLoginState(error = it.message ?: "")
                }
            }

        }.launchIn(viewModelScope)
    }

    fun registration(registrationDTO: CustomerRegisterDTO) {
        customerRegistrationUseCase(registrationDTO).onEach {
            when (it) {
                is Resource.Loading -> {
                    _registration.value = CustomerRegistrationState(isLoading = true)
                }
                is Resource.Success -> {
                    _registration.value = CustomerRegistrationState(data = it.data)
                }
                is Resource.Error -> {
                    _registration.value = CustomerRegistrationState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}
