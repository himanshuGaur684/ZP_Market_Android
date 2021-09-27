package com.gaur.zpmarket.domain.use_case.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.pagination.zp_assured.ZPAssurredPagingSource
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.Product
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetZPAssuredProductsUseCase @Inject constructor(private val dataSourcesInterface: DataSourcesInterface) {


    fun invoke(): Flow<PagingData<Product>> {
        return Pager(
            pagingSourceFactory = { ZPAssurredPagingSource(dataSourcesInterface) },
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            )
        ).flow
    }


}