package com.gaur.zpmarket.local.ui.cart

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gaur.zpmarket.domain.use_case.cart.GetCartListUseCase
import com.gaur.zpmarket.presentation.home.CustomerHomeRepository
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.cart.CartPostDTO
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
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository,
    private val homeRepository: CustomerHomeRepository,
    private val getCartListUseCase: GetCartListUseCase
) : ViewModel() {

    private val _postCart =
        MutableStateFlow<Events<Result<ServerMessageDTO>>>(Events(Result.empty()))
    val postCart: StateFlow<Events<Result<ServerMessageDTO>>> = _postCart

    private val customerId = MutableLiveData<String>()

    val cartList = customerId.switchMap {
        cartRepository.getCartPagination().asLiveData().cachedIn(viewModelScope)
    }

    fun postCustomerId(id: String) = customerId.postValue(id)

    fun postCart(postBody: CartPostDTO) = viewModelScope.launch {
        _postCart.value = Events(Result(Status.LOADING, null, null))
        _postCart.value = Events(cartRepository.postCart(postBody))
    }

    fun insertRecentlyViewedProduct(product: Product) = viewModelScope.launch {
        homeRepository.insertRecentlyViewedProduct(product)
    }
}
