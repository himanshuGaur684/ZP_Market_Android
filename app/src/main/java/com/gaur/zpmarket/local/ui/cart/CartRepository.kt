package com.gaur.zpmarket.local.ui.cart

import android.content.SharedPreferences
import android.util.Log
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.cart.CartPagingSource
import com.gaur.zpmarket.pagination.cart.cart_paging_response.Cart
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.cart.CartPostBody
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.utils.CustomerConstants
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest

class CartRepository(
    private val customerRetrofitInterface: CustomerRetrofitInterface,
    private val dataSourcesInterface: DataSourcesInterface,
    private val sharedPreferences: SharedPreferences
) {


    suspend fun postCart(postBody: CartPostBody): Result<ServerMessage> {
        return SafeApiRequest.handleApiCall { customerRetrofitInterface.postCart(postBody) }
    }


    fun getCartPagination(
        customerId: String = sharedPreferences.getString(
            CustomerConstants.CUSTOMER_ID,
            ""
        ).toString()
    ): kotlinx.coroutines.flow.Flow<PagingData<Cart>> {
        Log.d("TAG", "getCartPagination: ${customerId}")
        return Pager(config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            prefetchDistance = 5
        ),
            pagingSourceFactory = {
                CartPagingSource(customerId, dataSourcesInterface, 10)
            }).flow
    }


}