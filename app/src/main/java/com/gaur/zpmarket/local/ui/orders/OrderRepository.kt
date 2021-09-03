package com.gaur.zpmarket.local.ui.orders

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.orders.Order
import com.gaur.zpmarket.pagination.orders.OrderPagingSource
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.utils.CustomerConstants
import kotlinx.coroutines.flow.Flow

class OrderRepository(private val sharedPreferences: SharedPreferences, private val customerRetrofitInterface: CustomerRetrofitInterface) {

    fun getPaginatedOrder(
        customerId: String = sharedPreferences.getString(
            CustomerConstants.CUSTOMER_ID,
            ""
        ).toString()
    ): Flow<PagingData<Order>> {

        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { OrderPagingSource(customerId, customerRetrofitInterface) }
        ).flow
    }
}
