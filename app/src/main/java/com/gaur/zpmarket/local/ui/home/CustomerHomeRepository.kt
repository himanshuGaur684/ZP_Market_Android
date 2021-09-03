package com.gaur.zpmarket.local.ui.home

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.gaur.zpmarket.local.room.ZPMarketDao
import com.gaur.zpmarket.pagination.categories_products.CategoriesProductPagingSource
import com.gaur.zpmarket.pagination.new_products.NewProductsPagingSource
import com.gaur.zpmarket.pagination.zp_assured.ZPAssurredPagingSource
import com.gaur.zpmarket.remote.CustomerRetrofitInterface
import com.gaur.zpmarket.remote.DataSourcesInterface
import com.gaur.zpmarket.remote.response_customer.Product
import com.gaur.zpmarket.remote.response_customer.category.CategoryResponse
import com.gaur.zpmarket.remote.response_customer.home.HomeResponse
import com.gaur.zpmarket.utils.Result
import com.gaur.zpmarket.utils.SafeApiRequest
import com.gaur.zpmarket.utils.Status
import kotlinx.coroutines.flow.Flow
import java.lang.Exception

class CustomerHomeRepository(
    private val retrofitInterface: CustomerRetrofitInterface,
    private val dataSourcesInterface: DataSourcesInterface,
    private val zpMarketDao: ZPMarketDao
) {

    suspend fun getHomeResponse(): Result<HomeResponse> {

        try{
            val response = retrofitInterface.getHomePageDetails()
            if(response.isSuccessful){
                Result(Status.SUCCESS,response.body(),null)
            }else{
                Result(Status.ERROR,null,"Something went wrong ")
            }
        }catch (e:Exception){
            Result(Status.ERROR,null,e.message)
        }

        return SafeApiRequest.handleApiCall { retrofitInterface.getHomePageDetails() }
    }


    suspend fun getCategoriesList(): Result<CategoryResponse> {
        return SafeApiRequest.handleApiCall { dataSourcesInterface.getAllCategory() }
    }

    fun getNewestProducts(): Flow<PagingData<Product>> {
        return Pager(config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
        ),
            pagingSourceFactory = { NewProductsPagingSource(dataSourcesInterface) }
        ).flow
    }


    fun getZpAssuredProducts(): Flow<PagingData<Product>> {
        return Pager(
            pagingSourceFactory = { ZPAssurredPagingSource(dataSourcesInterface) },
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            )
        ).flow
    }


    fun getParticularCategoriesProductList(id: String): Flow<PagingData<Product>> {
        return Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = false,
                prefetchDistance = 5
            ),
            pagingSourceFactory = { CategoriesProductPagingSource(id, dataSourcesInterface) }).flow
    }


    suspend fun recentlyViewedProducts(products:Product){
        val list =zpMarketDao.getAllRecentlyViewedProducts()
        if(list.size==6){
            zpMarketDao.deleteRecentlyViewedProducts(list[5])
        }
        zpMarketDao.insertRecentlyViewedProducts(products)
    }

    suspend fun getLimitedRecentlyViewedProducts():Result<List<Product>>{
        return Result(Status.SUCCESS,zpMarketDao.getAllRecentlyViewedProducts(),null)
    }

    suspend fun insertRecentlyViewedProduct(product: Product){
        zpMarketDao.insertRecentlyViewedProducts(product)
    }

}