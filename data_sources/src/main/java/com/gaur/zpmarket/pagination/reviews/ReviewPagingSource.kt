package com.gaur.zpmarket.pagination.reviews

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.gaur.zpmarket.pagination.reviews.review_paging_response.PagingReviewResponse
import com.gaur.zpmarket.pagination.reviews.review_paging_response.Review
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.utils.SafeApiRequest


private const val STARTING_PAGE_INDEX = 1

class ReviewPagingSource(
    private val productId: String?,
    private val customerId: String?,
    private val dataSourcesInterface: DataSourcesInterface
) :
    PagingSource<Int, Review>() {


    override fun getRefreshKey(state: PagingState<Int, Review>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Review> {
        return try {
            val position = params.key ?: STARTING_PAGE_INDEX
            val response = SafeApiRequest.handleApiCall {
                return@handleApiCall if (productId != null) {
                    dataSourcesInterface.getProductReview(
                        productId = productId,
                        position,
                        10
                    )
                } else {
                    dataSourcesInterface.getCustomerReviews(customerId!!, position, 10)
                }
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