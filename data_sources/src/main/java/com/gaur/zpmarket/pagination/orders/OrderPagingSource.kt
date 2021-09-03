package com.gaur.zpmarket.pagination.orders

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.utils.SafeApiRequest


const val STARTING_PAGE_INDEX = 1

class OrderPagingSource(
    private val customerId: String,
    private val customerRetrofitInterface: CustomerRetrofitInterface
) : PagingSource<Int, Order>() {


    override fun getRefreshKey(state: PagingState<Int, Order>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Order> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = SafeApiRequest.handleApiCall {
                customerRetrofitInterface.getOrderPaginatedList(
                    customerId,
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