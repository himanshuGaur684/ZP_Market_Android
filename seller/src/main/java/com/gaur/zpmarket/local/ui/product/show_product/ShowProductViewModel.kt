package com.gaur.zpmarket.local.ui.product.show_product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShowProductViewModel @Inject constructor(private val repository: ShowProductRepositoryImpl) :
    ViewModel() {

    val productsStreams = repository.getProductsStream().cachedIn(viewModelScope)

    fun getAllProductCategory() = viewModelScope.launch {
        repository.getAllProductCategory()
    }
}
