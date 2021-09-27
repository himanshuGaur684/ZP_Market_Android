package com.gaur.zpmarket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.domain.repository.ProductSearchRepository
import com.gaur.zpmarket.pagination.search.SearchPagination
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.Flow

class ProductSearchRepositoryImpl(private val customerRetrofitInterface: CustomerRetrofitInterface) :
    ProductSearchRepository {
    override fun getSearchPagingProducts(q: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10, enablePlaceholders = false, prefetchDistance = 5
            ),
            pagingSourceFactory = { SearchPagination(q, customerRetrofitInterface) }
        ).flow
    }
}