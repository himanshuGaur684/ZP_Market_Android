package com.gaur.zpmarket.presentation.home_details

import androidx.lifecycle.*
import androidx.paging.cachedIn
import com.gaur.zpmarket.common.Resource
import com.gaur.zpmarket.domain.use_case.home.GetCategorieProductsUseCase
import com.gaur.zpmarket.domain.use_case.home.GetCatogriesListUseCase
import com.gaur.zpmarket.domain.use_case.home.GetNewestProductUseCase
import com.gaur.zpmarket.domain.use_case.home.GetZPAssuredProductsUseCase
import com.gaur.zpmarket.presentation.home.CategoriesListState
import com.gaur.zpmarket.remote.response_customer.category.Category
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class CustomerHomeDetailsViewModel @Inject constructor(
    private val getCategoryListUseCase: GetCatogriesListUseCase,
    private val getNewestProductUseCase: GetNewestProductUseCase,
    private val getZPAssuredProductsUseCase: GetZPAssuredProductsUseCase,
    private val getCategorieProductsUseCase: GetCategorieProductsUseCase,
    private val savedStateHandle: SavedStateHandle
) :
    ViewModel() {


    private val _categoriesList = MutableStateFlow<CategoriesListState>(CategoriesListState())
    val categoriesList: StateFlow<CategoriesListState> = _categoriesList

    val newestProductFlow = getNewestProductUseCase.invoke().cachedIn(viewModelScope)

    val zpAssuredFlow = getZPAssuredProductsUseCase.invoke().cachedIn(viewModelScope)

    val categoriesId = MutableLiveData<Category>()
    val categoriesProductList = categoriesId.switchMap {
        getCategorieProductsUseCase.invoke(it._id).asLiveData().cachedIn(viewModelScope)
    }

    fun getAllCategories() {
        getCategoryListUseCase().onEach {

            when (it) {
                is Resource.Loading -> {
                    _categoriesList.value = CategoriesListState(isLoading = true)

                }
                is Resource.Error -> {
                    _categoriesList.value = CategoriesListState(error = it.message ?: "")
                }
                is Resource.Success -> {
                    _categoriesList.value =
                        CategoriesListState(data = it.data?.category ?: emptyList())
                }
            }

        }.launchIn(viewModelScope)
    }

}