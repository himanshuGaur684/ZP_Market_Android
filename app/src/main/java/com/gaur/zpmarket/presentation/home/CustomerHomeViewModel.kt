package com.gaur.zpmarket.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.use_case.home.GetCatogriesListUseCase
import com.gaur.zpmarket.domain.use_case.home.GetHomeResponseUseCase
import com.gaur.zpmarket.domain.use_case.home.GetRecentlyViewedProductsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomerHomeViewModel @Inject constructor(
    private val repository: CustomerHomeRepository,
    private val getHomeResponseUseCase: GetHomeResponseUseCase,
    private val recentlyViewedProductUseCase: GetRecentlyViewedProductsUseCase,
    private val getCategoryListUseCase: GetCatogriesListUseCase
) :
    ViewModel() {

    private val _homeResponse = MutableStateFlow<CustomerHomeState>(CustomerHomeState())
    val homeResponse: StateFlow<CustomerHomeState> = _homeResponse


    private val _recentlyViewedProducts =
        MutableStateFlow<RecentlyViewedProductState>(RecentlyViewedProductState())
    val recentlyViewedProducts: StateFlow<RecentlyViewedProductState> = _recentlyViewedProducts

    init {
        getHomeResponse()
        getAllRecentlyViewedProducts()
    }


    fun getHomeResponse() {
        getHomeResponseUseCase().onEach {
            when (it) {
                is Resource.Loading -> {
                    _homeResponse.value = CustomerHomeState(isLoading = true)
                }
                is Resource.Error -> {
                    _homeResponse.value = CustomerHomeState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _homeResponse.value = CustomerHomeState(data = it.data)
                }
            }
        }.launchIn(viewModelScope)
    }


    fun getAllRecentlyViewedProducts() {
        recentlyViewedProductUseCase().onEach {
            _recentlyViewedProducts.value = RecentlyViewedProductState(data = it)
        }.launchIn(viewModelScope)
    }
}
