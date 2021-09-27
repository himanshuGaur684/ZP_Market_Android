package com.gaur.zpmarket.domain.repository

import androidx.paging.PagingData
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.CategoryDTO
import com.gaur.zpmarket.remote.response_customer.home.CustomerHomeDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

interface CustomerHomeRepository {


    suspend fun getHomeResponse(): Response<CustomerHomeDTO>

    suspend fun getCategoriesList(): Response<CategoryDTO>

    fun getNewestProducts(): Flow<PagingData<Product>>

    fun getZpAssuredProducts(): Flow<PagingData<Product>>

    fun getParticularCategoriesProductList(id: String): Flow<PagingData<Product>>

    suspend fun recentlyViewedProducts(products: Product)

    suspend fun getLimitedRecentlyViewedProducts(): List<Product>

    suspend fun insertRecentlyViewedProduct(product: Product)
}