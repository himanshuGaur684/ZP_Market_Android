package com.gaur.zpmarket.remote

import com.gaur.zpmarket.pagination.cart.cart_paging_response.CartPagingResponse
import com.gaur.zpmarket.pagination.categories_products.CategoriesProductListResponse
import com.gaur.zpmarket.pagination.new_products.NewestProductPagingResponse
import com.gaur.zpmarket.pagination.reviews.review_paging_response.PagingReviewResponse
import com.gaur.zpmarket.pagination.reviews.review_paging_response.Review
import com.gaur.zpmarket.pagination.zp_assured.ZpAssuredPagingResponse
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.CategoryResponse
import com.gaur.zpmarket.remote.response_customer.reviews.add_review.AddReviewPostBody
import com.gaur.zpmarket.remote.response_seller.ServerMessage
import retrofit2.Response
import retrofit2.http.*

interface DataSourcesInterface {


    @GET("/products/category")
    suspend fun getAllCategory(): Response<CategoryResponse>


    @GET("products/zp_assured")
    suspend fun getZPAssuredLists(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<ZpAssuredPagingResponse>

    @GET("products/newest_products")
    suspend fun getNewestProducts(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<NewestProductPagingResponse>


    @GET("products/products/{id}")
    suspend fun getSingleProduct(
        @Path("id") productId:String
    ):Response<Product>

    @GET("products/category/{id}")
    suspend fun getParticularCategoriesProductList(
        @Path("id") id: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<CategoriesProductListResponse>


    @GET("product/review/{productId}")
    suspend fun getProductReview(
        @Path("productId") productId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<PagingReviewResponse>

    @GET("product/review/{productId}")
    suspend fun getProductReviewOnly5(
        @Path("productId") productId: String
    ): Response<PagingReviewResponse>


    @POST("product/review/{productId}")
    suspend fun postProductReview(
        @Header("Authorization") token: String,
        @Path("productId") productId: String, @Body addReviewPostBody: AddReviewPostBody
    ): Response<ServerMessage>


    @GET("product/review/customer/{customerId}")
    suspend fun getCustomerReviews(
        @Path("customerId") customerId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<PagingReviewResponse>


    @GET("customer/cart/{customerId}")
    suspend fun getCustomerCart(
        @Path("customerId") customerId: String,
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): Response<CartPagingResponse>

    @DELETE("product/review/{reviewId}")
    suspend fun deleteReview(
        @Header("Authorization") token: String,
        @Path("reviewId") reviewId: String
    ): Response<ServerMessage>

    @PUT("product/review/{id}")
    suspend fun updateReview(@Body review: Review): Response<ServerMessage>


}