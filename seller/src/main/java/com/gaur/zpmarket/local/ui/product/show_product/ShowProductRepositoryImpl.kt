package com.gaur.zpmarket.local.ui.product.show_product

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.SellerRetrofitInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.utils.SafeApiRequest
import kotlinx.coroutines.flow.Flow

class ShowProductRepositoryImpl(
    private val retrofitInterface: SellerRetrofitInterface,
    private val dataSourcesInterface: DataSourcesInterface,
    private val databaseDAO: ZPMarketDao
) {


    fun getProductsStream(): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                prefetchDistance = 5,
                enablePlaceholders = false,
                initialLoadSize = 10
            ), pagingSourceFactory = { ShowProductsPagingSource(retrofitInterface) }
        ).flow
    }


    suspend fun getAllProductCategory() {
        val result = SafeApiRequest.handleApiCall { dataSourcesInterface.getAllCategory() }

        result.data?.let {
            databaseDAO.insertAllCategory(it.category)
        }

    }

}