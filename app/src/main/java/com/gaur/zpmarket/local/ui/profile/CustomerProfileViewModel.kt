package com.gaur.zpmarket.local.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.remote.response_customer.profile.CustomerProfileResponse
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerProfileViewModel @Inject constructor(private val repository: CustomerProfileRepository) :
    ViewModel() {

    private val _profile = MutableStateFlow<Events<Result<CustomerProfileResponse>>>(
        Events(
            Result.empty()
        )
    )

    val profile: StateFlow<Events<Result<CustomerProfileResponse>>> = _profile


    fun getCustomerProfile(customerId: String) = viewModelScope.launch {
        _profile.value = Events(Result(Status.LOADING, null, null))
        _profile.value = Events(repository.getCustomerProfile(customerId))
    }


}