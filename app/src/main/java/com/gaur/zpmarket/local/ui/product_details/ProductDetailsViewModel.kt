package com.gaur.zpmarket.local.ui.product_details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(private val productDetailsRepository: ProductDetailsRepository) : ViewModel() {

    private val _product: MutableStateFlow<Events<Result<Product>>> =
        MutableStateFlow(Events(Result.empty()))
    val product: StateFlow<Events<Result<Product>>> = _product


    fun getSingleProduct(id:String)= viewModelScope.launch {
        _product.value = Events(Result(Status.LOADING,null,null))
        _product.value = Events(productDetailsRepository.getSingleProduct(id))
    }

}