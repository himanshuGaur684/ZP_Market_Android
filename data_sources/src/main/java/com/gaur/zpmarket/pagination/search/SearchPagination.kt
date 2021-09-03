package com.gaur.zpmarket.pagination.search

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gaur.zpmarket.pagination.search.response.SearchPagingResponse
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.utils.SafeApiRequest

private const val STARTING_PAGE_INDEX = 1

class SearchPagination(
    private val q: String,
    private val customerRetrofitInterface: CustomerRetrofitInterface

) :
    PagingSource<Int, Product>() {

    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = SafeApiRequest.handleApiCall<SearchPagingResponse> {
                customerRetrofitInterface.searchProductQuery(q, position, 10)
            }
            LoadResult.Page(
                response.data?.results!!,
                prevKey = if (position == 0) null else position - 1,
                nextKey = if (response.data.results.size <10) null else position + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}
