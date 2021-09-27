package com.gaur.zpmarket.presentation.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.use_case.profile.GetProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomerProfileViewModel @Inject constructor(
    private val getProfileUseCase: GetProfileUseCase
) :
    ViewModel() {

    private val _profile = MutableStateFlow<CustomerProfileState>(CustomerProfileState())
    val profile: StateFlow<CustomerProfileState> = _profile


    fun getCustomerProfile(customerId: String) {
        getProfileUseCase(customerId).onEach {
            when (it) {
                is Resource.Loading -> {
                    _profile.value = CustomerProfileState(isLoading = true)
                }
                is Resource.Success -> {
                    _profile.value = CustomerProfileState(data = it.data)
                }
                is Resource.Error -> {
                    _profile.value = CustomerProfileState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}
