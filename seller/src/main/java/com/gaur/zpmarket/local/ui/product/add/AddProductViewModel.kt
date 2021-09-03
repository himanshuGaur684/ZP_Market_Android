package com.gaur.zpmarket.local.ui.product.add

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.remote.response_seller.products.SingleProductResponse
import com.gaur.zpmarket.utils.Events
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import okhttp3.MultipartBody
import okhttp3.RequestBody
import javax.inject.Inject

@HiltViewModel
class AddProductViewModel @Inject constructor(private val repository: AddProductRepository) :
    ViewModel() {

    val addObservables = AddProductObservables("", "", "", "", "", "", "", false)

    private val _postProduct =
        MutableStateFlow<Events<Result<ServerMessage>>>(Events(Result.empty()))
    val postProduct: StateFlow<Events<Result<ServerMessage>>> = _postProduct

    private val _product =
        MutableStateFlow<Events<Result<SingleProductResponse>>>(Events(Result.empty()))
    val product: StateFlow<Events<Result<SingleProductResponse>>> = _product

    private val _delete = MutableStateFlow<Events<Result<ServerMessage>>>(Events(Result.empty()))
    val delete: StateFlow<Events<Result<ServerMessage>>> = _delete

    var categoryList = HashMap<String, String>()

    var editCategoryList = MutableLiveData<HashMap<String, String>>()

    fun postProduct(
        image: List<MultipartBody.Part>,
        name: RequestBody,
        categoryId: RequestBody,
        description: RequestBody,
        discount: RequestBody,
        discountPrice: RequestBody,
        marketPrice: RequestBody,
        sellerId: RequestBody,
        productDetails: RequestBody,
        productFeatures: RequestBody,
        productPackaging: RequestBody,
        requestBodyCOD: Boolean
    ) = viewModelScope.launch {

        _postProduct.value = Events(Result(Status.LOADING, null, null))
        _postProduct.value = Events(
            repository.postProducts(
                image,
                name,
                categoryId,
                description,
                discount,
                discountPrice,
                marketPrice,
                sellerId,
                productDetails,
                productFeatures,
                productPackaging,
                requestBodyCOD
            )
        )
    }

    fun editProducts(
        id: String,
        name: String,
        categoryId: String,
        description: String,
        discount: String,
        discountPrice: String,
        marketPrice: String,
        productFeatures: String,
        productDetails: String,
        packagingDetails: String,
        cashOnDelivery: Boolean
    ) = viewModelScope.launch {
        _postProduct.value = Events(Result(Status.LOADING, null, null))
        _postProduct.value = Events(
            repository.editProducts(
                id = id,
                name = name,
                categoryId = categoryId,
                description = description,
                discount = discount,
                discountPrice = discountPrice,
                marketPrice = marketPrice,
                productFeatures = productFeatures,
                productDetails = productDetails,
                packagingDetails = packagingDetails,
                cashOnDelivery = cashOnDelivery
            )
        )
    }

    fun deleteProduct(id: String) = viewModelScope.launch {
        _delete.value = Events(Result(Status.LOADING, null, null))
        _delete.value = Events(repository.deleteProduct(id))
    }

    fun getAllProductCategory() = viewModelScope.launch {
        repository.getAllProductCategory().forEach {
            categoryList[it.name] = it._id
        }
    }

    fun getEditTextProductCategory() = viewModelScope.launch {
        Result(Status.LOADING, null, null)
        val k = repository.getAllProductCategory()
        val m = HashMap<String, String>()
        for (i in k.indices) {
            m[k[i].name] = k[i]._id
        }
        editCategoryList.postValue(m)
        Result(Status.SUCCESS, m, null)
    }

    fun getSingleProduct(id: String) = viewModelScope.launch {
        _product.value = Events(Result(Status.LOADING, null, null))
        _product.value = Events(repository.getSingleProduct(id))
    }
}
