package com.gaur.zpmarket.pagination.categories_products

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.utils.SafeApiRequest



private const val STARTING_PAGE_INDEX = 1

class CategoriesProductPagingSource(private val id:String,private val dataSourcesInterface: DataSourcesInterface) :
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
            val response = SafeApiRequest.handleApiCall<CategoriesProductListResponse> {
                dataSourcesInterface.getParticularCategoriesProductList(
                    id,
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