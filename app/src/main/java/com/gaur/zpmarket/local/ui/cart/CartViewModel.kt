package com.gaur.zpmarket.local.ui.cart

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gaur.zpmarket.local.ui.home.CustomerHomeRepository
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.cart.CartPostBody
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val homeRepository: CustomerHomeRepository
) : ViewModel() {

    private val _postCart = MutableStateFlow<Events<Result<ServerMessage>>>(Events(Result.empty()))
    val postCart: StateFlow<Events<Result<ServerMessage>>> = _postCart

    private val customerId = MutableLiveData<String>()

    val cartList = customerId.switchMap {
        cartRepository.getCartPagination().asLiveData().cachedIn(viewModelScope)
    }

    fun postCustomerId(id: String) = customerId.postValue(id)

    fun postCart(postBody: CartPostBody) = viewModelScope.launch {
        _postCart.value = Events(Result(Status.LOADING, null, null))
        _postCart.value = Events(cartRepository.postCart(postBody))
    }

    fun insertRecentlyViewedProduct(product: Product) = viewModelScope.launch {
        homeRepository.insertRecentlyViewedProduct(product)
    }
}
