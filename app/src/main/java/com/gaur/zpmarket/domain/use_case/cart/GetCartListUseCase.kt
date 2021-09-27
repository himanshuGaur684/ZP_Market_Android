package com.gaur.zpmarket.domain.use_case.cart

import androidx.paging.PagingData
import com.gaur.zpmarket.domain.repository.CartRepository
import com.gaur.zpmarket.pagination.cart.cart_paging_response.Cart
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetCartListUseCase @Inject constructor(private val repository: CartRepository) {


    fun invoke(customerId: String): Flow<PagingData<Cart>> =
        repository.getCartPagination(customerId)


}