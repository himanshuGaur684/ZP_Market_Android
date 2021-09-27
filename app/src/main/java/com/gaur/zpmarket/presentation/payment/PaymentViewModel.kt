package com.gaur.zpmarket.presentation.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.use_case.payments.GetOrderIdUseCase
import com.gaur.zpmarket.domain.use_case.payments.PostOrderUseCase
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val postOrderUseCase: PostOrderUseCase,
    private val getOrderIdUseCase: GetOrderIdUseCase
) : ViewModel() {

    private val _postOrderId: MutableStateFlow<PostOrderIdState> =
        MutableStateFlow(PostOrderIdState())
    val postOrderId: StateFlow<PostOrderIdState> = _postOrderId

    private val _paymentOrderId: MutableStateFlow<PaymentOrderIdState> =
        MutableStateFlow(PaymentOrderIdState())
    val paymentOrderId: StateFlow<PaymentOrderIdState> = _paymentOrderId

    fun postOrder(order: PostOrder) {
        postOrderUseCase(order).onEach {
            when (it) {
                is Resource.Loading -> {
                    _postOrderId.value = PostOrderIdState(isLoading = true)
                }
                is Resource.Success -> {
                    _postOrderId.value = PostOrderIdState(data = it.data)
                }
                is Resource.Error -> {
                    _postOrderId.value = PostOrderIdState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }

    fun getOrderId(amount: Int) {
        getOrderIdUseCase(amount).onEach {
            when (it) {
                is Resource.Loading -> {
                    _paymentOrderId.value = PaymentOrderIdState(isLoading = true)
                }
                is Resource.Success -> {
                    _paymentOrderId.value = PaymentOrderIdState(data = it.data)
                }
                is Resource.Error -> {
                    _paymentOrderId.value = PaymentOrderIdState(error = it.message ?: "")
                }
            }
        }.launchIn(viewModelScope)
    }
}
