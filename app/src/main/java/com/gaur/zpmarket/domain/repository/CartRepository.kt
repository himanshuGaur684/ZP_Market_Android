package com.gaur.zpmarket.domain.repository

import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.cart.cart_paging_response.Cart
import com.gaur.zpmarket.remote.response_customer.cart.CartPostDTO
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CartRepository {

    suspend fun postCart(postBody: CartPostDTO): Response<ServerMessageDTO>


    fun getCartPagination(customerId: String): Flow<PagingData<Cart>>

}