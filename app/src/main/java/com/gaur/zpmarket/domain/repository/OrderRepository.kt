package com.gaur.zpmarket.domain.repository

import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.orders.Order
import kotlinx.coroutines.flow.Flow

interface OrderRepository {


    fun getPaginatedOrder(
        customerId: String
    ): Flow<PagingData<Order>>
}