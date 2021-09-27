package com.gaur.zpmarket.local.ui.reviews

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.reviews.ReviewPagingSource
import com.gaur.zpmarket.pagination.reviews.review_paging_response.PagingReviewResponse
import com.gaur.zpmarket.pagination.reviews.review_paging_response.Review
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.reviews.add_review.AddReviewPostBody
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest
import kotlinx.coroutines.flow.Flow

class CustomerReviewRepository(private val dataSourcesInterface: DataSourcesInterface) {

    fun getPagingReviews(productId: String): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                ReviewPagingSource(
                    productId,
                    null,
                    dataSourcesInterface
                )
            }
        ).flow
    }

    fun getCustomerReviews(customerId: String): Flow<PagingData<Review>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = {
                ReviewPagingSource(
                    null,
                    customerId,
                    dataSourcesInterface
                )
            }
        ).flow
    }

    suspend fun getCustomerReviewOnly5(productId: String): Result<PagingReviewResponse> {
        return SafeApiRequest.handleApiCall { dataSourcesInterface.getProductReviewOnly5(productId) }
    }

    suspend fun postProductReview(
        token: String,
        productId: String,
        addReviewPostBody: AddReviewPostBody
    ): Result<ServerMessageDTO> {
        return SafeApiRequest.handleApiCall {
            dataSourcesInterface.postProductReview(
                token,
                productId,
                addReviewPostBody
            )
        }
    }

    suspend fun deleteReview(token: String, reviewId: String): Result<ServerMessageDTO> {
        return SafeApiRequest.handleApiCall {
            dataSourcesInterface.deleteReview(
                reviewId = reviewId,
                token = token
            )
        }
    }

    suspend fun updateReviews(review: Review): Result<ServerMessageDTO> {
        return SafeApiRequest.handleApiCall { dataSourcesInterface.updateReview(review) }
    }
}
