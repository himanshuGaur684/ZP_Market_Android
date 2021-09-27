package com.gaur.zpmarket.data.repository

import android.content.SharedPreferences
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.domain.repository.CartRepository
import com.gaur.zpmarket.pagination.cart.CartPagingSource
import com.gaur.zpmarket.pagination.cart.cart_paging_response.Cart
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.cart.CartPostDTO
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CartRepositoryImpl(
    private val dataSourcesInterface: DataSourcesInterface,
    private val sharedPreferences: SharedPreferences,
    private val customerRetrofitInterface: CustomerRetrofitInterface
) : CartRepository {

    override suspend fun postCart(postBody: CartPostDTO): Response<ServerMessageDTO> {
        return customerRetrofitInterface.postCart(postBody)
    }

    override fun getCartPagination(customerId: String): Flow<PagingData<Cart>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                CartPagingSource(customerId, dataSourcesInterface, 10)
            }
        ).flow
    }
}