package com.gaur.zpmarket.local.ui.reviews

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gaur.zpmarket.pagination.reviews.review_paging_response.PagingReviewResponse
import com.gaur.zpmarket.pagination.reviews.review_paging_response.Review
import com.gaur.zpmarket.remote.response_customer.reviews.add_review.AddReviewPostBody
import com.gaur.zpmarket.remote.response_seller.ServerMessageDTO
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerReviewViewModel @Inject constructor(private val customerReviewRepository: CustomerReviewRepository) :
    ViewModel() {

    private val productId = MutableLiveData<String>()

    private val customerId = MutableLiveData<String>()

    fun postProductId(id: String) {
        productId.postValue(id)
    }

    val customerReviewList = customerId.switchMap {
        customerReviewRepository.getCustomerReviews(it).asLiveData().cachedIn(viewModelScope)
    }

    val reviewList =
        productId.switchMap { customerReviewRepository.getPagingReviews(it).asLiveData() }

    private val _reviewListOnly5 =
        MutableStateFlow<Events<Result<PagingReviewResponse>>>(Events(Result.empty()))
    val reviewListOnly5: StateFlow<Events<Result<PagingReviewResponse>>> = _reviewListOnly5

    private val _postReview =
        MutableStateFlow<Events<Result<ServerMessageDTO>>>(Events(Result.empty()))
    val postReview: StateFlow<Events<Result<ServerMessageDTO>>> = _postReview

    private val _deleteReview =
        MutableStateFlow<Events<Result<ServerMessageDTO>>>(Events(Result.empty()))
    val deleteReview: StateFlow<Events<Result<ServerMessageDTO>>> = _deleteReview

    private val _updateReview =
        MutableStateFlow<Events<Result<ServerMessageDTO>>>(Events(Result.empty()))
    val updateReview: StateFlow<Events<Result<ServerMessageDTO>>> = _updateReview

    fun getReviewOnly5(productId: String) = viewModelScope.launch {
        _reviewListOnly5.value = Events(Result(Status.LOADING, null, null))
        _reviewListOnly5.value = Events(customerReviewRepository.getCustomerReviewOnly5(productId))
    }

    fun postReview(token: String, addReviewPostBody: AddReviewPostBody, productId: String) =
        viewModelScope.launch {
            _postReview.value = Events(Result(Status.LOADING, null, null))
            _postReview.value =
                Events(
                    customerReviewRepository.postProductReview(
                        token,
                        productId,
                        addReviewPostBody
                    )
                )
        }

    fun deleteReview(token: String, reviewId: String) = viewModelScope.launch {
        _deleteReview.value = Events(Result(Status.LOADING, null, null))
        _deleteReview.value = Events(customerReviewRepository.deleteReview(token, reviewId))
    }

    fun postCustomerId(it: String) {
        customerId.postValue(it)
    }

    fun updateReview(review: Review) = viewModelScope.launch {
        _updateReview.value = Events(Result(Status.LOADING, null, null))
        _updateReview.value = Events(customerReviewRepository.updateReviews(review))
    }
}
