package com.gaur.zpmarket.domain.repository

import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.remote.response_customer.payment.OrderIdDTO
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import retrofit2.Response

interface RazorPayPaymentRepository {

    suspend fun postOrder(order: PostOrder): Response<ServerMessageDTO>

    suspend fun getOrderId(amount: Int): Response<OrderIdDTO>
}