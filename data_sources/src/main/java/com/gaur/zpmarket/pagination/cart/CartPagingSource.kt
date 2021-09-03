package com.gaur.zpmarket.pagination.cart

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gaur.zpmarket.pagination.cart.cart_paging_response.Cart
import com.gaur.zpmarket.pagination.cart.cart_paging_response.CartPagingResponse
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.utils.SafeApiRequest





private const val STARTING_PAGE_INDEX = 1

class CartPagingSource(
    private val customerId: String,
    private val dataSourcesInterface: DataSourcesInterface,
    private val limit:Int
) :
    PagingSource<Int, Cart>() {


    override fun getRefreshKey(state: PagingState<Int, Cart>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cart> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = SafeApiRequest.handleApiCall<CartPagingResponse> {
                dataSourcesInterface.getCustomerCart(
                    customerId = customerId,
                    position,
                    10
                )
            }
            LoadResult.Page(
                response.data?.results!!,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (response.data.results.isNullOrEmpty()) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}