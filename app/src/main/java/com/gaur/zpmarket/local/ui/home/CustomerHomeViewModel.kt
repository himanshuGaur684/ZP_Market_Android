package com.gaur.zpmarket.local.ui.home

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.Category
import com.gaur.zpmarket.remote.response_customer.category.CategoryResponse
import com.gaur.zpmarket.remote.response_customer.home.HomeResponse
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CustomerHomeViewModel @Inject constructor(private val repository: CustomerHomeRepository) :
    ViewModel() {

    private val _homeResponse =
        MutableStateFlow<Events<Result<HomeResponse>>>(Events(Result.empty()))
    val homeResponse: StateFlow<Events<Result<HomeResponse>>> = _homeResponse

    private val _categoriesList =
        MutableStateFlow<Events<Result<CategoryResponse>>>(Events(Result.empty()))
    val categoriesList: StateFlow<Events<Result<CategoryResponse>>> = _categoriesList

    val newestProductFlow = repository.getNewestProducts().cachedIn(viewModelScope)

    val zpAssuredFlow = repository.getZpAssuredProducts().cachedIn(viewModelScope)

    val categoriesId = MutableLiveData<Category>()
    val categoriesProductList = categoriesId.switchMap {
        repository.getParticularCategoriesProductList(it._id).asLiveData().cachedIn(viewModelScope)
    }

    private val _recentlyViewedProducts = MutableStateFlow<Events<Result<List<Product>>>>(
        Events(
            Result.empty()
        )
    )
    val recentlyViewedProducts: StateFlow<Events<Result<List<Product>>>> = _recentlyViewedProducts

    init {
        getHomeResponse()
        getAllRecentlyViewedProducts()
    }

    fun getHomeResponse() = viewModelScope.launch {
        _homeResponse.value = Events(Result(Status.LOADING, null, null))
        _homeResponse.value = Events(repository.getHomeResponse())
    }

    fun getAllCategories() = viewModelScope.launch {
        _categoriesList.value = Events(Result(Status.LOADING, null, null))
        _categoriesList.value = Events(repository.getCategoriesList())
    }

    fun getAllRecentlyViewedProducts() = viewModelScope.launch {
        _recentlyViewedProducts.value = Events(Result(Status.LOADING, null, null))
        _recentlyViewedProducts.value = Events(repository.getLimitedRecentlyViewedProducts())
    }
}
