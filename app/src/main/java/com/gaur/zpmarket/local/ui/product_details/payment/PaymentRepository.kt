package com.gaur.zpmarket.local.ui.product_details.payment

import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.order.PostOrder
import com.gaur.zpmarket.remote.response_customer.payment.OrderIdResponse
import com.gaur.zpmarket.remote.response_customer.payment.OrderPrice
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class PaymentRepository(private val customerRetrofitInterface: CustomerRetrofitInterface) {

    suspend fun postOrder(order: PostOrder): Result<ServerMessage> {
        return SafeApiRequest.handleApiCall { customerRetrofitInterface.postOrder(order) }
    }

    suspend fun getOrderId(amount:Int):Result<OrderIdResponse>{
        return SafeApiRequest.handleApiCall { customerRetrofitInterface.getOrderId(OrderPrice(amount)) }
    }


}