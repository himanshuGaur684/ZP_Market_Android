package com.gaur.zpmarket.remote

import com.gaur.zpmarket.remote.response_seller.ServerMessage
import com.gaur.zpmarket.remote.response_seller.login.SellerLoginObject
import com.gaur.zpmarket.remote.response_seller.products.EditProduct
import com.gaur.zpmarket.remote.response_seller.products.SellerProductResponse
import com.gaur.zpmarket.remote.response_seller.products.SingleProductResponse
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenBody
import com.gaur.zpmarket.remote.response_seller.refresh_token.RefreshTokenResponse
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationObject
import com.gaur.zpmarket.remote.response_seller.register.SellerRegistrationResponseObject
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface SellerRetrofitInterface {

    /**--------------------------------------- Auth ---------------------------------**/

    @POST("ecom/seller/register")
    suspend fun sellerRegistration(
        @Body sellerRegistrationObject: SellerRegistrationObject
    ): Response<SellerRegistrationResponseObject>

    @POST("ecom/seller/login")
    suspend fun sellerLogin(
        @Body sellerLoginBody: SellerLoginObject
    ): Response<SellerRegistrationResponseObject>

    @POST("custumer/refresh_token")
    suspend fun refreshToken(@Body refreshTokenBody: RefreshTokenBody): Response<RefreshTokenResponse>

    /**-----------------------  Get All Seller Products------------------------**/

    @GET("ecom/seller/products")
    suspend fun getAllSellerProducts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<SellerProductResponse>

    /**------------------------  Products  ---------------------------------------**/

    @Multipart
    @POST("ecom/seller/products")
    suspend fun postProducts(
        @Header("Authorization") token: String,
        @Part image: List<MultipartBody.Part>,
        @Part("name") name: RequestBody,
        @Part("categoryId") categoryId: RequestBody,
        @Part("description") description: RequestBody,
        @Part("discount") discount: RequestBody,
        @Part("discountPrice") discountPrice: RequestBody,
        @Part("marketPrice") marketPrice: RequestBody,
        @Part("sellerId") sellerId: RequestBody,
        @Part("productDetails") productDetails: RequestBody,
        @Part("productFeatures") productFeatures: RequestBody,
        @Part("packagingDetails") productPackaging: RequestBody,
        @Part("cashOnDelivery") requestBodyCOD: Boolean
    ): Response<ServerMessage>

    @GET("ecom/seller/products/{id}")
    suspend fun getSingleProduct(@Path("id") id: String): Response<SingleProductResponse>

    @PATCH("ecom/seller/products/{id}")
    suspend fun editProducts(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Body req: EditProduct
    ): Response<ServerMessage>

    @DELETE("ecom/seller/products/{id}")
    suspend fun deleteProduct(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Response<ServerMessage>
}
