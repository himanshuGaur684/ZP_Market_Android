package com.gaur.zpmarket.domain.use_case.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.new_products.NewProductsPagingSource
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetNewestProductUseCase @Inject constructor(private val dataSourcesInterface: DataSourcesInterface) {

    fun invoke(): Flow<PagingData<Product>> {
        return Pager(
            pagingSourceFactory = { NewProductsPagingSource(dataSourcesInterface) },
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            )
        ).flow
    }
}