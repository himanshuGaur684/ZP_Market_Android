package com.gaur.zpmarket.local.ui.product.add

import android.content.SharedPreferences
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.remote.SellerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.category.Category
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.remote.response_seller.products.EditProduct
import com.gaur.zpmarket.remote.response_seller.products.SingleProductResponse
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest
import com.gaur.zpmarket.utils.SellerConstants
import okhttp3.MultipartBody
import okhttp3.RequestBody

class AddProductRepository(
    private val zpMarketDao: ZPMarketDao,
    private val sellerRetrofitInterface: SellerRetrofitInterface,
    private val sharedPreferences: SharedPreferences
) {


    suspend fun postProducts(
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
    ): Result<ServerMessage> {
        return SafeApiRequest.handleApiCall {
            sellerRetrofitInterface.postProducts(
                token = sharedPreferences.getString(SellerConstants.TOKEN, "").toString(),
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
        }

    }


    suspend fun editProducts(
        id: String,
        name: String,
        categoryId: String,
        description: String,
        discount: String,
        discountPrice: String,
        marketPrice: String,
        productDetails: String,
        productFeatures: String,
        packagingDetails: String,
        cashOnDelivery: Boolean

    ): Result<ServerMessage> {
        return SafeApiRequest.handleApiCall {
            sellerRetrofitInterface.editProducts(
                token = sharedPreferences.getString(SellerConstants.TOKEN, "").toString(),
                id,
                EditProduct(
                    name = name,
                    categoryId = categoryId,
                    description = description,
                    discount = discount,
                    discountPrice = discountPrice,
                    marketPrice = marketPrice,
                    productDetails = productDetails,
                    productFeatures = productFeatures,
                    cashOnDelivery = cashOnDelivery,
                    packagingDetails = packagingDetails
                )
            )
        }

    }


    suspend fun deleteProduct(id: String): Result<ServerMessage> {
        return SafeApiRequest.handleApiCall {
            sellerRetrofitInterface.deleteProduct(
                id = id,
                token = sharedPreferences.getString(SellerConstants.TOKEN, "").toString()
            )
        }
    }

    suspend fun getAllProductCategory(): List<Category> {
        return zpMarketDao.getAllCategory()
    }

    suspend fun getSingleProduct(id: String): Result<SingleProductResponse> {
        return SafeApiRequest.handleApiCall { sellerRetrofitInterface.getSingleProduct(id) }
    }

}