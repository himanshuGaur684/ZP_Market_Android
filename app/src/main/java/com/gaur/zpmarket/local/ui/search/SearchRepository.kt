package com.gaur.zpmarket.local.ui.search

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.search.SearchPagination
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.Flow

class SearchRepository(private val customerRetrofitInterface: CustomerRetrofitInterface) {


    fun getSearchPagingProducts(q: String): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(
            pageSize = 10, enablePlaceholders = false, prefetchDistance = 5
        ), pagingSourceFactory = { SearchPagination(q, customerRetrofitInterface) }).flow
    }


}