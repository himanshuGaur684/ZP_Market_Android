package com.gaur.zpmarket.data.repository

import com.gaur.zpmarket.domain.repository.RazorPayPaymentRepository
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.remote.response_customer.payment.OrderIdDTO
import com.gaur.zpmarket.remote.response_customer.payment.OrderPriceDTO
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import retrofit2.Response

class RazorPayPaymentRepositoryImpl(private val customerRetrofitInterface: CustomerRetrofitInterface) :
    RazorPayPaymentRepository {

    override suspend fun postOrder(order: PostOrder): Response<ServerMessageDTO> {
        return customerRetrofitInterface.postOrder(order)
    }

    override suspend fun getOrderId(amount: Int): Response<OrderIdDTO> {
        return customerRetrofitInterface.getOrderId(OrderPriceDTO(amount))
    }
}