package com.gaur.zpmarket.local.ui.product_details.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.remote.response_customer.payment.OrderIdResponse
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val paymentRepository: PaymentRepository) :
    ViewModel() {

    private val _orderResponse: MutableStateFlow<Events<Result<ServerMessage>>> = MutableStateFlow(
        Events(
            Result.empty()
        )
    )
    val orderResponse: StateFlow<Events<Result<ServerMessage>>> = _orderResponse


    private val _orderId: MutableStateFlow<Events<Result<OrderIdResponse>>> = MutableStateFlow(
        Events(
            Result.empty()
        )
    )
    val orderId: StateFlow<Events<Result<OrderIdResponse>>> = _orderId


    fun postOrder(order: PostOrder) = viewModelScope.launch {
        _orderResponse.value = Events(Result(Status.LOADING, null, null))
        _orderResponse.value = Events(paymentRepository.postOrder(order))
    }

    fun getOrderId(amount: Int) = viewModelScope.launch {
        _orderId.value = Events(Result(Status.LOADING, null, null))
        _orderId.value = Events(paymentRepository.getOrderId(amount))
    }


}