package com.gaur.zpmarket.domain.repository

import androidx.paging.PagingData
import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.Flow

interface ProductSearchRepository {


    fun getSearchPagingProducts(q: String): Flow<PagingData<Product>>


}