package com.gaur.zpmarket.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.domain.repository.CustomerHomeRepository
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.pagination.categories_products.CategoriesProductPagingSource
import com.gaur.zpmarket.pagination.zp_assured.ZPAssurredPagingSource
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.CategoryDTO
import com.gaur.zpmarket.remote.response_customer.home.CustomerHomeDTO
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class CustomerHomeRepositoryImpl(
    private val customerRetrofitInterface: CustomerRetrofitInterface,
    private val dataSourcesInterface: DataSourcesInterface,
    private val zpMarketDao: ZPMarketDao
) : CustomerHomeRepository {

    override suspend fun getHomeResponse(): Response<CustomerHomeDTO> {
        return customerRetrofitInterface.getHomePageDetails()
    }

    override suspend fun getCategoriesList(): Response<CategoryDTO> {
        return dataSourcesInterface.getAllCategory()
    }

    override fun getNewestProducts(): Flow<PagingData<Product>> {
        return Pager(
            pagingSourceFactory = { ZPAssurredPagingSource(dataSourcesInterface) },
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            )
        ).flow
    }

    override fun getZpAssuredProducts(): Flow<PagingData<Product>> {
        return Pager(
            pagingSourceFactory = { ZPAssurredPagingSource(dataSourcesInterface) },
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            )
        ).flow
    }

    override fun getParticularCategoriesProductList(id: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { CategoriesProductPagingSource(id, dataSourcesInterface) }
        ).flow
    }

    override suspend fun recentlyViewedProducts(products: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun getLimitedRecentlyViewedProducts(): List<Product> {
        return zpMarketDao.getAllRecentlyViewedProducts()
    }

    override suspend fun insertRecentlyViewedProduct(product: Product) {
        zpMarketDao.insertRecentlyViewedProducts(product)
    }
}