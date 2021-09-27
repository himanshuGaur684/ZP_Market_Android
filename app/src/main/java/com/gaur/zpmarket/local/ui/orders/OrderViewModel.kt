package com.gaur.zpmarket.local.ui.orders

import androidx.lifecycle.ViewModel
import com.gaur.zpmarket.domain.use_case.order.GetOrdersUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class OrderViewModel @Inject constructor(private val getOrdersUseCase: GetOrdersUseCase) :
    ViewModel() {
    val orderList = getOrdersUseCase.invoke()
}
